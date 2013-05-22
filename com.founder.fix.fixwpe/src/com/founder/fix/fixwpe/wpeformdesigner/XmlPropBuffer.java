package com.founder.fix.fixwpe.wpeformdesigner;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.founder.fix.apputil.util.JSONUtil;
import com.founder.fix.apputil.util.XmlUtil;
import com.founder.fix.ocx.platformdesigner.interfaces.FixStudioInterface;
import com.founder.fix.ocx.util.DHtmlConst;
import com.founder.fix.ocx.util.FormConst;
import com.founder.fix.ocx.util.FormConst.ElementInfo;
import com.founder.fix.ocx.util.Utility;

/**fix.studio.form.html.properties.xml解释类
 * @author BB
 *
 */
public class XmlPropBuffer 
{
	private String htmlFileName = null;
	private String webProjectRealPath = null;
	private String projectRealPath = null;
	private Document document = null;
	
	public XmlPropBuffer(String f)
	{
		this.htmlFileName = f;
		this.webProjectRealPath = FixStudioInterface.getWebProjectRealPathFromHtmlRealPath(htmlFileName);
		this.projectRealPath = Utility.getProjectRealPath();
		
		this.init(FormConst.PROPERTIESFILENAME);
	}
	
	/**
	 * 结构化的表单属性配置
	 * "input.text":[{"name":"input.text","caption":"文本框",......},{"name":"ReadOnly","combobox":["true","false"]}] 
	 * 		
	 */
	private HashMap<String,ArrayList<HashMap<String,Object>>> StudioConfigXml = new HashMap<String,ArrayList<HashMap<String,Object>>>();
	public void init(String filename)
	{
		this.StudioConfigXml.clear();
		
		InputStream is=Utility.class.getResourceAsStream("/resources/xml/"+filename);
		if ( is != null )
			Read2Buffer(is);
		else
		{
			//先找HTML属性文件
			filename = this.projectRealPath + "resources/xml/"+filename;
			Read2Buffer(filename);
		}
		//最后遍历这个WEB下的所有组件
		this.scanComponents();
		
		//查找有 element属性的并且showstate="replace" 的
		this.replaceProperty();
	}
	
	
	
	/**
	 * 查找组件
	 */
	private void scanComponents()
	{
		//再找组件属性文件
		String projPath = this.webProjectRealPath;
		if ( projPath != null )
		{
			projPath += "/WebRoot/components";
			//准备遍历这个目录下的所有文件夹
			//只要具备了 config.xml,design.html即是组件
			
			File file = new File(projPath);
			File[] list=file.listFiles();
	        for(int i=0;i<list.length;i++)
	        {
	        	File p = list[i];
	        	if ( p.isDirectory() )
	        	{
	        		File[] fs = p.listFiles();
	        		int comp = 0;
	        		File comf = null;
	        		for ( int j = 0; j < fs.length; j++ )
	        		{
	        			File f = fs[j];
	        			if ( f.isFile() )
	        			{
	        				if ( f.getName().toLowerCase().equals("config.xml")||f.getName().toLowerCase().equals("design.html")
	        						||f.getName().toLowerCase().equals("ico.bmp"))
	        				{
	        					comp++;
	        					if ( f.getName().toLowerCase().equals("config.xml") )
	        					  comf = f;
	        				}
	        			}
	        		}
	        		if ( comp == 3 )
	        		{
	        			Read2Buffer(comf.getAbsolutePath());
	        		}
	        	}
	        }
		}
	}
	
	/**把XML文件读入缓存
	 * @param fileName
	 */
	private void Read2Buffer(InputStream is)
	{
		try 
		{
			document = XmlUtil.read(is);
			String a = document.getPath();
			Element root = XmlUtil.getRootElement(document);
			setXmlNodes2Buffer(root,null);
		} 
		catch (DocumentException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**把XML文件读入缓存
	 * @param fileName
	 */
	private void Read2Buffer(String fileName)
	{
		try 
		{
			document = XmlUtil.read(fileName);
			
			Element root = XmlUtil.getRootElement(document);
			
			if ( root.attribute("ref") != null )
			{
				String f = root.attributeValue("ref");
				if ( !f.equals("") )
				{
					String[] fs = f.split(",");
					for ( int i = 0; i < fs.length; i++ )
					{
						String parentPath = (new File(fileName)).getParentFile().getPath();
						String tmpf = parentPath+"/"+fs[i];						
						Read2Buffer(tmpf);
					}
				}
				
			}
			
			setXmlNodes2Buffer(root,null);
			
			//System.out.println(this.getPropertyListJSON("input.text"));
			//System.out.println(this.getPropertyJSON("input.text","DataFormat"));
		} 
		catch (DocumentException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	/**通过递归读取属性节点
	 * @param parent
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setXmlNodes2Buffer(Element parent,String elementname)
	{
		String ElementName = elementname;
		//顺序列表
		ArrayList<HashMap<String,Object>> propertylist = null;
		//控件属性
		HashMap<String,Object> property = null;
		if ( elementname != null )
		{
			propertylist = (ArrayList)this.StudioConfigXml.get(elementname);
			property = propertylist.get(propertylist.size()-1);
		}
		
		
		for ( int i = 0; i < parent.elements().size(); i++ )
		{
			Element node = (Element)parent.elements().get(i);
			String name = node.getName().toLowerCase();
			if ( name.equals("element") )
			{
				propertylist = new ArrayList<HashMap<String,Object>>();
				property = new HashMap<String,Object>();
				propertylist.add(property);
				ElementName = node.attribute("name").getValue().toLowerCase();	
				if ( this.StudioConfigXml.containsKey(ElementName) )
					this.StudioConfigXml.remove(ElementName);
				this.StudioConfigXml.put(ElementName, propertylist);
			}
			else if ( name.equals("property") )
			{
				property = new HashMap<String,Object>();
				propertylist.add(property);
			}
			//下拉放到list
			else if ( name.equals("combobox") )
			{
				ArrayList<String> combobox = new ArrayList<String>();
				property.put("combobox", combobox);
				for ( int k = 0; k < node.elements().size(); k++ )
				{
					Element subnode = (Element)node.elements().get(k);
					String comlistval = subnode.attribute("value").getValue();
					combobox.add(comlistval);
				}
				
				continue;
			}
			//控件引用的JS文件
			else if ( name.equals("jsref") )
			{
				ArrayList<String> jsref = new ArrayList<String>();
				property.put("jsref", jsref);
				for ( int k = 0; k < node.elements().size(); k++ )
				{
					Element subnode = (Element)node.elements().get(k);
					String comlistval = subnode.attribute("value").getValue();
					jsref.add(comlistval);
				}
				
				continue;
			}
			//组件引用的样式文件
			else if ( name.equals("cssref") )
			{
				ArrayList<String> cssref = new ArrayList<String>();
				property.put("cssref", cssref);
				for ( int k = 0; k < node.elements().size(); k++ )
				{
					Element subnode = (Element)node.elements().get(k);
					String comlistval = subnode.attribute("value").getValue();
					cssref.add(comlistval);
				}
				
				continue;
			}
			//描述要取大文本
			//description/jsonconfig/instanse/default/eventdefault
			else if ( FormConst.XmlConfigTextItem.containsKey(name) )
			{
				boolean isTrim = FormConst.XmlConfigTextItem.get(name);
				String text = "";
				if (isTrim)
					text = node.getTextTrim();
				else
					text = node.getText();
				property.put(name, text);
				continue;
			}
				
			for ( int j = 0; j < node.attributeCount(); j ++ )
			{
				Attribute attributeObj = node.attribute(j);
				String aname = attributeObj.getName();
				String avalue= attributeObj.getValue();
				if ( node.getName().equals("element") && aname.equals("name") )
					avalue = avalue.toLowerCase();
				if ( aname.equals("element") )
					avalue = avalue.toLowerCase();
				property.put(aname, avalue);
			}
			
			setXmlNodes2Buffer(node, ElementName);
		}
	}
	
	/**取属性配置集合对象
	 * @return
	 */
	public HashMap<String,ArrayList<HashMap<String,Object>>> getStudioConfigXml()
	{
		return StudioConfigXml;
	}
	
	/**取属性配置集合对象的JSON
	 * @return
	 */
	public String getStudioConfigXmlJSON()
	{
		String jsonStr = JSONUtil.parseObject2JSON(StudioConfigXml);
		return jsonStr;
	}
	
	/**
	 * 取组件高级属性
	 * @param name
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> getPropertyList(ElementInfo elementInfo)
	{
		ArrayList<HashMap<String,Object>> listMain = new ArrayList<HashMap<String,Object>>();
		ArrayList<HashMap<String,Object>> list = this.getPropertyList(elementInfo.name.toLowerCase());		
		for ( int i = 0; i < list.size(); i++ )
		{
			HashMap<String,Object> map = list.get(i);
			Object categoryObj = map.get("category");
			if ( categoryObj == null ) categoryObj = "normal";
			String category = categoryObj.toString();
			
			if ( i == 0 )
			{
				listMain.add(map);
			}
			else if ( elementInfo.categorys==null || category.equals(elementInfo.categorys) )
			{
				Object level = map.get("level");
				//在子属性窗口，配了LEVEL=1的话，就不显示了
				if ( level == null || level.toString().equals("") )
					listMain.add(map);
				else
				{
					boolean isInner = true;
					String[] levels = null;
					if ( level.toString().startsWith("+") || level.toString().startsWith("-") )
					{
						isInner = level.toString().startsWith("+");
						level = level.toString().substring(1);
					}
					
					String tmpLevels = ","+level+",";
					String tmpLel = ","+elementInfo.level+",";
					if (( tmpLevels.indexOf(tmpLel)>-1 && isInner )
							|| (tmpLevels.indexOf(tmpLel)==-1 && !isInner) )
						listMain.add(map);
					
				}
			}
		}
		
		return listMain;
	}
	
	/**取到某个控件的属性列表的List
	 * @param name
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> getPropertyList(String name)
	{
		ArrayList<HashMap<String,Object>> propertylist = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> property = new HashMap<String,Object>();
		
		if ( !this.StudioConfigXml.containsKey(name))
		{
			property.put("name", name);
			property.put("caption", name);
			propertylist.add(property);			
			this.StudioConfigXml.put(name, propertylist);
		}
		else
		{
			//已经存在的属性，查看一下有没有element的属性，有就需要从那里取过来继承的属性
			propertylist = this.StudioConfigXml.get(name);
			for ( int i = propertylist.size()-1; i >= 0; i-- )
			{
				property = propertylist.get(i);
				if ( property.containsKey("element") && !property.containsKey("HasElement") )
				{
//					property.put("HasElement", "true");
//					String key = property.get("element").toString();
//					ArrayList<HashMap<String,Object>> list = this.StudioConfigXml.get(key);
//					if ( list != null )
//					for ( int j = 0; j < list.size(); j++ )
//					{
//						propertylist.add(i+1, list.get(j));
//					}
				}
			}
		}
			
		return this.StudioConfigXml.get(name);
		
	}
	
	
	/**取到某个控件的属性列表的Json
	 * @param name
	 * @return
	 */
	public String getPropertyListJSON(String name)
	{
		ArrayList<HashMap<String,Object>> list = getPropertyList(name);
		String jsonStr = JSONUtil.parseObject2JSON(list);
		return jsonStr;
	}
	
	
	/**取到某个控件的某个属性的Map
	 * @param name 控件名:input.text
	 * @param key 属性名:class (name="class")
	 * @return
	 */
	public HashMap<String,Object> getProperty(String name, String key)
	{
		name = name.toLowerCase();
		ArrayList<HashMap<String,Object>> list = getPropertyList(name);
		
		for ( int i = 0; i < list.size(); i++ )
		{
			HashMap<String,Object> map = list.get(i);
			if ( map.get("name").equals(key) )
			{
				return map;
			}
		}
		return null;
	}
	
	/**取到某个控件的某个属性的JSON
	 * @param name 控件名:input.text
	 * @param key 属性名:class (name="class")
	 * @return
	 */
	public String getPropertyJSON(String name, String key)
	{
		HashMap<String,Object> map = getProperty(name, key);
		String jsonStr = null;
		if ( map != null)
			jsonStr = JSONUtil.parseObject2JSON(map);
		return jsonStr;
		
	}
	
	/**
	 * 取控件tagName
	 * @return
	 */
	public ArrayList<String> getComponentsTagName(String TriggerBy)
	{
		ArrayList<String> list2 = new ArrayList<String>();
		
		Iterator<String> iterator = this.StudioConfigXml.keySet().iterator();
		while ( iterator.hasNext() )
		{
			String key = iterator.next();
			ArrayList<HashMap<String,Object>> list = this.StudioConfigXml.get(key);
			HashMap<String,Object> map2 = list.get(0);
			Object compType = map2.get("componenttype");
			if ( compType != null && compType.toString().equals(DHtmlConst.COMPONENT_TYPE) )
			{				
				if ( TriggerBy == null || TriggerBy.equals("") )
				{
					String name = map2.get("name").toString();
					Object tagName = map2.get("tagname");
					list2.add(tagName.toString());
				}
				else
				{
					Object TriggerBy1 = map2.get("TriggerBy");
					if ( TriggerBy1!=null && TriggerBy1.toString().equals(TriggerBy) )
					{
						String name = map2.get("name").toString();
						Object tagName = map2.get("tagname");
						list2.add(tagName.toString());
					}
				}
			}
		}
		return list2;
		
	}
	
	/**取控件列表 
	 * 	
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> getComponentList()
	{
		ArrayList<HashMap<String,Object>> CompList = new ArrayList<HashMap<String,Object>>();
		
		Iterator<String> iterator = this.StudioConfigXml.keySet().iterator();
		while ( iterator.hasNext() )
		{
			String key = iterator.next();
			ArrayList<HashMap<String,Object>> list = this.StudioConfigXml.get(key);
			HashMap<String,Object> map2 = list.get(0);
			Object compType = map2.get("componenttype");
			if ( compType != null && compType.toString().equals(DHtmlConst.COMPONENT_TYPE) )
			{
				String name = map2.get("name").toString();
				//String caption = map2.get("caption").toString();
				String path = (new File(this.webProjectRealPath+"/WebRoot/components/"+name+"")).toString();
				if ( map2.containsKey("componentpath") ) map2.remove("componentpath");
				map2.put("componentpath", path);
				CompList.add(map2);
			}
		}

		return CompList;
	}
	
	/**
	 * 返回组件的JSON
	 * @return
	 */
	public String getComponentJson()
	{
		String jsonStr = JSONUtil.parseObject2JSON(this.getComponentList());
		return jsonStr;
	}
	
	/**
	 * 替换showstate="replace"
	 */
	private void replaceProperty()
	{
		Iterator<String> iterator = this.StudioConfigXml.keySet().iterator();
		while ( iterator.hasNext() )
		{
			String key = iterator.next();

//			if ( key.equals("input|text") )
//			{
//				System.out.print("");
//			}
			
			//用以记录删除的索引
			ArrayList<Integer> dels = new ArrayList<Integer>();
			
			ArrayList<HashMap<String,Object>> list = this.getPropertyList(key);
			//第一个不要，跟key 是重复的
			for ( int i = 1; i < list.size(); i++ )
			{
				HashMap<String,Object> map2 = list.get(i);
				//找到是需要替换的部分
				if ( map2.containsKey("element") && "replace".equals(map2.get("showstate")) )
				{
					//list.remove(i);
					dels.add(i);
					
					String key1 = map2.get("element").toString();
					ArrayList<HashMap<String,Object>> list1 = this.getPropertyList(key1);
					for ( int j = 1; j < list1.size();j++ )
					{
						HashMap<String,Object> map3 = list1.get(j);
						//list.add(map3);
						list.add(i+j, map3);
					}
				}
				
				
			}
			
			//根据索引删除列表中需要删除的
			for ( int i = dels.size()-1; i >= 0; i-- )
			{
				int idx = dels.get(i);
				list.remove(idx);
			}
			
		}
	}
	
	/**取属性配置的简单列表 
	 * 	"input.text":[{name:"ReadOnly",type:"normal"},{name:"",type:""}}] 
	 * @return
	 */
	public HashMap<String,ArrayList<HashMap<String,Object>>> getPropertySimpleList()
	{
		HashMap<String,ArrayList<HashMap<String,Object>>> map = new HashMap<String,ArrayList<HashMap<String,Object>>>();
		
		Iterator<String> iterator = this.StudioConfigXml.keySet().iterator();
		while ( iterator.hasNext() )
		{
			String key = iterator.next();
			ArrayList<HashMap<String,Object>> lists = new ArrayList<HashMap<String,Object>>();
			map.put(key, lists);
			
			ArrayList<HashMap<String,Object>> list = this.StudioConfigXml.get(key);
			//第一个不要，跟key 是重复的
			for ( int i = 1; i < list.size(); i++ )
			{
				HashMap<String,Object> map2 = list.get(i);
				String propName = map2.get("name").toString();
				String propType = "normal";
				String jsonType = "string";
				//取级别 normal,advance,event
				Object type = map2.get("category");
				if ( type != null && !type.toString().equals("")) propType = type.toString();
				//取jsontype string,object,array
				type = map2.get("jsontype");
				if ( type != null && !type.toString().equals("")) jsonType = type.toString();
				
				HashMap<String,Object> map3 = new HashMap<String, Object>();
				map3.put("name", propName);
				map3.put("type", propType);
				map3.put("jsontype", jsonType);
				map3.put("property", map2);
				lists.add(map3);
			}
			
		}
		
		return map;
	}
	
	/**取属性配置的简单列表 JSON
	 * @return
	 */
	public String getPropertySimpleListJSON()
	{
		HashMap<String,ArrayList<HashMap<String,Object>>> map = getPropertySimpleList();
		String jsonStr = null;
		if ( map != null)
			jsonStr = JSONUtil.parseObject2JSON(map);
		return jsonStr;
	}
}
