package com.founder.fix.jst.pagedesigner.properties;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.wst.html.core.internal.document.ElementStyleImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.founder.fix.fixwpe.formdesigner.ui.properties.editor.WPECustomDialogPropertyDescriptor;
import com.founder.fix.fixwpe.formdesigner.ui.properties.editor.WPEFormPropertyUtils;
import com.founder.fix.jst.pagedesigner.itemcreation.AbstractTagCreatorProvider;
import com.founder.fix.studio.formdesigner.common.FormConst;
import com.founder.fix.studio.formdesigner.common.FormPropertyUtils;
import com.founder.fix.util.JSonUtil;
import com.founder.fix.wst.html.core.internal.document.FixElementStyleImpl;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * @author Fifteenth
 */

public class FixPropertySource implements IPropertySource {
	
	private FixPropertySourceProvider provider;
	private String _propertyType;

	private FixElementStyleImpl proImpl;
	private ElementStyleImpl impl;
	
	//组件属性list
	private HashMap<String,ArrayList<HashMap<String,Object>>> _childPropertyMap = new HashMap<String,ArrayList<HashMap<String,Object>>>();
	
	/*
	 *	@author Fifteenth
	 *		模型json分为两部分
	 *		1.tag
	 *		2.fixAttr
	 */
	private JSONObject[] modelJsons = new JSONObject[2];
	
	private JSONObject[] childModelJsons = new JSONObject[2];
	
	
	public JSONObject[] getChildModelJsons() {
		return childModelJsons;
	}


	public void setChildModelJsons(JSONObject[] childModelJsons) {
		this.childModelJsons = childModelJsons;
	}


	private HashMap<String, String> _chanslateMap;



	private String[] childKeys= new String[2];
	
	private Object saveValue;
	private Object saveId;
	
	private boolean childSaveMark=false;
	
	
	public boolean isChildSaveMark() {
		return childSaveMark;
	}


	public void setChildSaveMark(boolean childSaveMark) {
		this.childSaveMark = childSaveMark;
	}


	
	
	public FixPropertySource(FixPropertySourceProvider provider,String propertyType,
			FixElementStyleImpl proImpl,ElementStyleImpl impl){
		this.provider = provider;
		this._propertyType = propertyType;
		this.proImpl = proImpl;
		this.impl = impl;
	}
	
	
	
	/**
	 * @author Fifteenth
	 *		其实就是接过fixImpl中的属性
	 */
	public void refleshPropertyJosn(String propetyType){
		
		modelJsons = new JSONObject[ConstantProperty.attributeJson.length];
			
		/*
		 *	@author Fifteenth
		 *		model&tag
		 */
		//tag
		modelJsons[ConstantProperty.attributeJsonTagIndex] = JSonUtil.getChildJosn(
				proImpl.getObjectJson(),
				ConstantProperty.childJsonAttributeTag);
		//fixAttr
		modelJsons[ConstantProperty.attributeJsonModelIndex] = JSonUtil.getChildJosn(
				proImpl.getObjectJson(),
				ConstantProperty.childJsonAttributeModel);
	}
	

	public Object getEditableValue() {
		return null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		
		_chanslateMap = new HashMap();
		
		refleshPropertyJosn(_propertyType);
		AbstractTagCreatorProvider.initProperty(
        		//D:\FormDesign\runtime-exe\test1\WebRoot\NewFile.jsp
				//ywpx
        		"D:/mySelfWorkSpaces/runtime-exe/test1/WebRoot/NewFile1.jsp"); //$NON-NLS-1$
		
		String tagName = impl.getAttribute("ComponentType").toLowerCase();
		if(tagName==null||tagName.equals("")){
			return new PropertyDescriptor[0];
		}
		
		List <HashMap<String,Object>>propertylist = AbstractTagCreatorProvider.
				globleXmlMap.get(tagName);
		
		List <PropertyDescriptor>descriptorList = new ArrayList<PropertyDescriptor>();
		
		int descriptorsCount = 0;
		
		for(int i=0;i<modelJsons.length;i++){
			if(modelJsons[i]!=null){
				Iterator jsonKeys = modelJsons[i].keys();
				
				while(jsonKeys.hasNext()){
					//stringTemp键值
					String key = jsonKeys.next().toString();
					
					/*
					 *	@author Fifteenth
					 *		tag_prop_map是组件的某个属性的map
					 */
					HashMap<String,Object> tag_prop_map = propertylist.get(descriptorsCount);
					
					Object categoryObj = tag_prop_map.get("category");
					
					
					if ( categoryObj == null ) {
						categoryObj = "normal";
					}
					
					if(categoryObj.toString().equals(_propertyType)){
						/*
						 *	
						 */
						String name = tag_prop_map.get("name").toString();
						String caption = tag_prop_map.get("caption").toString();
						
						/*
						 *	@author Fifteenth
						 *		1.name可以理解为_en
						 *	getPropertyValue和setPropertyValue传递的id值
						 *		2.caption解决为_cn
						 *		所以操作模型的时候需要_chanslateMap
						 */
						_chanslateMap.put(name, caption);
						
						Object displaytype = tag_prop_map.get("displaytype");
						if ( displaytype == null ) displaytype = "edit";
						String dispType = displaytype.toString();
						
						Object showstate = tag_prop_map.get("showstate");
						if ( showstate == null ) showstate = "normal";
						boolean readOnly = !showstate.toString().equals("normal");
						
						String category = categoryObj.toString();
						if ( FormConst.Categorys.containsKey(category) )
							category = FormConst.Categorys.get(category);
						List<String> comboboxlist = new ArrayList<String>();
						String[] comboboxs = new String[]{};
						if ( dispType.equals("combobox") )
						{
							//WPE版
							comboboxlist = (List<String>) tag_prop_map.get("combobox");
							
							if ( comboboxlist != null )
								comboboxs = (String[])comboboxlist.toArray(new String[]{});				
						}			
						
						PropertyDescriptor propDes = null;
						if ( dispType.equals("combobox") )
							//第一个参数是下面的id值来源
							propDes = new ComboBoxPropertyDescriptor(name,caption,comboboxs);
						else if ( dispType.equals("dialog") )
						{
							if(tag_prop_map.containsKey("element")){
								_childPropertyMap.put(name, AbstractTagCreatorProvider.
										globleXmlMap.get(tag_prop_map.get("element").toString()));
							}
							
							propDes = new WPECustomDialogPropertyDescriptor(
									tag_prop_map,modelJsons[i],_chanslateMap,AbstractTagCreatorProvider.
									globleXmlMap,tagName);
						}
//						else if ( descriptorsCount == 0 )
//						{
//							propDes = new PropertyDescriptor(FormConst.FIXCOMPONENTSKEY,FormConst.FIXCOMPONENTSKEYCN);
//						}
						else if ( readOnly )
						{
							propDes = new PropertyDescriptor(name,caption);
						}
						else 
							propDes = new TextPropertyDescriptor(name,caption);
						
						Object description = tag_prop_map.get("description");
						if ( description != null )
						{
							propDes.setDescription(description.toString());
						}
						
//						propDes.setCategory(ConstantProperty.attributeCategroy[i]);
						descriptorList.add(propDes);
						descriptorsCount++;
					}else{
						descriptorsCount++;
					}
				}
			}
		}
		
		
		int descriptorLength = descriptorList.size();
		PropertyDescriptor[] descriptors = new PropertyDescriptor[descriptorLength];
		
		for(int i=0;i<descriptorLength;i++){
			descriptors[i] = descriptorList.get(i);
		}
		return descriptors;
	}

	public Object getPropertyValue(Object id) {
		
		/*
		 *	@author Fifteenth
		 *		key是组件的某个属性
		 */
		String key_en = id.toString();
		/*
		 *	@author Fifteenth
		 *		tag_ttr_map是组件（第一个参数）的某个属性（第二个参数）的map
		 */
		Map<String,Object> tag_ttr_map = AbstractTagCreatorProvider.getXMLProperty(
				proImpl.getAttribute("ComponentType"), key_en);
		
		
		for(int i=0;i<modelJsons.length;i++){
			try {
				
				/*
				 *	@author Fifteenth
				 *		需要将key值翻译一下
				 */
				if(_chanslateMap.get(key_en)!=null){
					String tempKey_cn = _chanslateMap.get(key_en).toString();
					if(tag_ttr_map!=null&&modelJsons[i].has(tempKey_cn)){
						Object valueObject = modelJsons[i].get(tempKey_cn);
						
						/*
						 *	@author Fifteenth
						 *		分为有子节点和无子节点两种
						 *			有子节点又分为打开和弹出两种情况
						 */
						if(valueObject instanceof JSONObject){
							/*
							 *	@author Fifteenth
							 *		1. 根据配置子属性页或者弹出
							 * 
							 * 		2. 需要在这里触发调用子属性页
							 */
//							if(1==2){
//								//弹出
//								return valueObject;
//							}else{
								childModelJsons[i] = (JSONObject)valueObject;
								//子属性页
								FixSubAttributePropertySource fixSubPropertySourc = 
										new FixSubAttributePropertySource(this,id.toString(),i,_childPropertyMap);
								return fixSubPropertySourc;
//							}
						}else{
							//当不带子属性时，沿用ocx体系
							return ocxValue(valueObject,tag_ttr_map);
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ""; //$NON-NLS-1$
	}

	public boolean isPropertySet(Object id) {
		return false;
	}

	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unused")
	public void setPropertyValue(Object id, Object value) {
		/*
		 * @author Fifteenth
		 * 		该if 处理子节点修改属性
		 * 	1.id!=null，value==null
		 *	2.childSaveMark表示子节点已设置完成
		 */
		if(id!=null && value==null
				&& childSaveMark
				){
			
			String key_en = id.toString();
			
			for(int i=0;i<childKeys.length;i++){
				if(childModelJsons[i]!=null){
					childSaveMark = false;
					childKeys[i] =  id.toString();
					try {
						modelJsons[i].put(childKeys[i], childModelJsons[i]);
						setNodeValueAndReflesh(id.toString(),
								modelJsons[i].get(id.toString()));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		/*
		 *	@author Fifteenth
		 *		该if 处理非子节点修改属性
		 */
		if(value!=null && 
				(!id.equals(saveId) || !value.equals(saveValue))){
			if(value!=null){
				
				String key_en = id.toString();
				Map<String,Object> tag_ttr_map = AbstractTagCreatorProvider.getXMLProperty(
						proImpl.getAttribute("ComponentType"), key_en);
				
				for(int i=0;i<modelJsons.length;i++){
					/*
					 *	@author Fifteenth
					 *		modle的值是显示值，可以理解为汉化后
					 */
				
					String key_cn = _chanslateMap.get(key_en).toString();
					if(modelJsons[i]!=null){
						
						if(modelJsons[i].has(key_cn)){
							/*
							 * @author Fifteenth
							 * 		i=0:tag的属性
							 * 		i=1:model的属性
							 */
							saveValue = value;
							saveId = id;
							
							Object displaytype = tag_ttr_map.get("displaytype");
							Object showstate = tag_ttr_map.get("showstate");
							
							if (displaytype.equals("combobox")){	
								List<String> comboboxlist = WPEFormPropertyUtils.getPropComboList(modelJsons[i],
										showstate.toString(),tag_ttr_map);	
								
								value = comboboxlist.get(Integer.parseInt(value.toString()));
							}
							
							if(i==0){
								String category = ConstantProperty.attributeCategroy[i];
								impl.setAttribute(key_en, value.toString());
								provider.refleshTagProperty();
							}else if(i==1){
								setNodeValueAndReflesh(key_en,value.toString());
							}
						}
					}
				}
			}else{
				System.out.println("warning!!!!!!!!!!!!!!!position:FixPropertySource");
			}
		}
	}

	public void setNodeValueAndReflesh(String key_en,Object value)
	{
		/*
		 *	@author Fifteenth
		 *		1.model（模型）用_cn
		 *		2.方法参数用_en
		 *		3.通过_chanslateMap可以来回转
		 */
		String key_cn = _chanslateMap.get(key_en).toString();
		String modelName = ConstantProperty.fixAttributeNameSpace;
		NodeList list = impl.getChildNodes();
		/*
		 * @author Fifteenth
		 * 		得到对应modelName的json
		 */
		if(ModelCommet.getJson(
				list, _propertyType, modelName
				).has(key_cn)){
			/*
			 * @author Fifteenth
			 * 		得到对应modelName的节点
			 */
			Node node = ModelCommet.getNode(
					list, _propertyType, 
					modelName);
			
			/*
			 * @author Fifteenth
			 * 通过修改节点内容，设值
			 */
			String commentString = node.getTextContent().trim();
			
			String jsonString = commentString.substring(modelName.length(),
					commentString.length()); //$NON-NLS-1$
			
			JSONObject json;
			try {
				json = new JSONObject(jsonString);
				if(json.has(key_cn)){
					json.put(key_cn, value);
				}
				//设值
				node.setNodeValue(modelName+json.toString());
				//刷新
				provider.refleshModelProperty(); 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public static Object ocxValue(Object valueObject,Map tag_ttr_map){
		Object value = valueObject;
		Object category = tag_ttr_map.get("category");
		Object showstate = tag_ttr_map.get("showstate");
		if ( showstate == null ) showstate = "normal";
		
		//有引用
		if ( showstate.toString().equals("inherited") && tag_ttr_map.containsKey("element") )
		{
//			value = new ComponentRefProperty(this.mainEditor, prop.get("element").toString());
		}
		else if ( tag_ttr_map.containsKey("displaytype") 
				&& tag_ttr_map.get("displaytype").toString().equals("combobox") )
		{ 				
			List<String> list = (List<String>) tag_ttr_map.get("combobox");

			if ( value!=null && !value.equals("") )
			{
				//if ( value.toString().equals("1"))
				//	value = "true";
				//if ( value.toString().equals("0"))
				//	value = "false";
				value = list.indexOf(value);
			}
			else{
				if ( tag_ttr_map.containsKey("default") 
						&& !tag_ttr_map.get("default").toString().equals("") )
				{ 
					value = tag_ttr_map.get("default").toString();
					//向DHTML发送指令，以修改此属性
//					this.mainEditor.getDhtml().ChangOneProperty(id.toString(), 
//							value.toString(), category.toString());
					value = list.indexOf(value);
				}
				else{
					value = -1;
				}
			}
			
		}
		else if ( (value==null || value.equals("")) && 
				tag_ttr_map.containsKey("default") 
				&& !tag_ttr_map.get("default").toString().equals("") )
		{ 
			
			value = tag_ttr_map.get("default").toString();
			//向DHTML发送指令，以修改此属性
			Object jsontype = tag_ttr_map.get("jsontype");
			if((jsontype ==null || jsontype.equals("string")
//					|| jsontype.equals("object")
					)
					&& category.toString().equals("advance")){
//				this.mainEditor.getDhtml().ChangOneProperty(id.toString(), 
//						"\""+value.toString()+"\"", category.toString());
			}
			else{
//				this.mainEditor.getDhtml().ChangOneProperty(id.toString(), 
//						value.toString(), category.toString());
			}
		}
			
		//事件
//		if ( "event".equals(category) )
//		{
//			
//			String ctlId = FixControlKey;
//			if ( map.containsKey("id") )
//				ctlId = map.get("id").toString();
//			value = ctlId + "_" + id;
//		}
		
		return  value;
	}
}
