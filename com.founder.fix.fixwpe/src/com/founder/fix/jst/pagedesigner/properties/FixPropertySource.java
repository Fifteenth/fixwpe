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
import com.founder.fix.jst.pagedesigner.itemcreation.AbstractTagCreatorProvider;
import com.founder.fix.studio.formdesigner.common.FormConst;
import com.founder.fix.studio.formdesigner.common.FormPropertyUtils;
import com.founder.fix.util.JSonUtil;
import com.founder.fix.wst.html.core.internal.document.PropertyElementStyleImpl;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * @author Fifteenth
 */

public class FixPropertySource implements IPropertySource {
	
	private FixPropertySourceProvider provider;

	private PropertyElementStyleImpl proImpl;
	private ElementStyleImpl impl;
	
	
	private JSONObject[] propertyJsons;
	
	private JSONObject[] childPropertyJsons = new JSONObject[2];
	
	
	private Map _chanslateMap;



	private String[] childKeys= new String[2];
	
	private String propetyType;
	
	private Object saveValue;
	private Object saveId;
	
	private boolean childSaveMark=false;
	
	
	public boolean isChildSaveMark() {
		return childSaveMark;
	}


	public void setChildSaveMark(boolean childSaveMark) {
		this.childSaveMark = childSaveMark;
	}


	public JSONObject[] getChildPropertyJsons() {
		return childPropertyJsons;
	}
	
	public void setChildPropertyJsons(JSONObject[] childPropertyJsons) {
		this.childPropertyJsons = childPropertyJsons;
	}
	
	
	public FixPropertySource(FixPropertySourceProvider provider,PropertyElementStyleImpl proImpl,ElementStyleImpl impl,String propetyType){
		this.provider = provider;
		this.proImpl = proImpl;
		this.impl = impl;
		this.propetyType = propetyType;
	}
	
	
	
	/**
	 * @author Fifteenth
	 * 得到model中的属性
	 */
	public void refleshPropertyJosn(String propetyType){
		
		// 需要区分attribute还是even....
		if(propetyType.equals(ConstantProperty.propetyTypeAttribute)){
			propertyJsons = new JSONObject[ConstantProperty.attributeJson.length];
			for(int i=0;i<ConstantProperty.attributeJson.length;i++){
				if(ConstantProperty.childJsonAttribute[i].equals(
						ConstantProperty.childJsonAttributeModel)){
					for(int ii=0;ii<ConstantProperty.attributeCategroyModelName.length;
							ii++){
						propertyJsons[i] = JSonUtil.getChildJosn(
								proImpl.getObjectJson(),ConstantProperty.attributeCategroyModelName[ii]);
					}
				}else{
					propertyJsons[i] = JSonUtil.getChildJosn(
							proImpl.getObjectJson(),ConstantProperty.childJsonAttribute[i]);
				}
				
			}
		}else if(propetyType.equals(ConstantProperty.propetyTypeEven)){
			propertyJsons = new JSONObject[ConstantProperty.evenJson.length];
			for(int i=0;i<ConstantProperty.evenJson.length;i++){
				propertyJsons[i] = JSonUtil.getChildJosn(
						proImpl.getObjectJson(),ConstantProperty.evenCategroyModelName[i]);
			}
		}
	}
	

	public Object getEditableValue() {
		return null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		
		_chanslateMap = new HashMap();
		
		refleshPropertyJosn(propetyType);
		AbstractTagCreatorProvider.initProperty(
        		//D:\FormDesign\runtime-exe\test1\WebRoot\NewFile.jsp
        		"D:/mySelfWorkSpaces/runtime-exe/ywpx/WebRoot/NewFile1.jsp"); //$NON-NLS-1$
		
		String tagName = impl.getAttribute("ComponentType").toLowerCase();
		if(tagName==null||tagName.equals("")){
			return new PropertyDescriptor[0];
		}
		ArrayList<HashMap<String,Object>> list = AbstractTagCreatorProvider.
				globleXmlMap.get(tagName);
		
		
		
		PropertyDescriptor[] descriptors = new PropertyDescriptor[getDescriptorLength()];	
		int descriptorsCount = 0;
		
		// 不需要知道是attribute还是even
		for(int i=0;i<propertyJsons.length;i++){
			if(propertyJsons[i]!=null){
				Iterator jsonKeys = propertyJsons[i].keys();
				
				while(jsonKeys.hasNext()){
					
					//stringTemp键值
					String key = jsonKeys.next().toString();
					
					/*
					 *	@author Fifteenth
					 *		tag_attr_map是组件的某个属性的map
					 */
					HashMap<String,Object> tag_attr_map = list.get(descriptorsCount);
					String name = tag_attr_map.get("name").toString();
					String caption = tag_attr_map.get("caption").toString();
					
					/*
					 *	@author Fifteenth
					 *		wpe版需要翻译一下
					 */
					_chanslateMap.put(name, caption);
					
					Object displaytype = tag_attr_map.get("displaytype");
					if ( displaytype == null ) displaytype = "edit";
					String dispType = displaytype.toString();
					
					Object showstate = tag_attr_map.get("showstate");
					if ( showstate == null ) showstate = "normal";
					boolean readOnly = !showstate.toString().equals("normal");
					
					Object categoryObj = tag_attr_map.get("category");
					if ( categoryObj == null ) categoryObj = "normal";
					String category = categoryObj.toString();
					if ( FormConst.Categorys.containsKey(category) )
						category = FormConst.Categorys.get(category);
					List<String> comboboxlist = new ArrayList<String>();
					String[] comboboxs = new String[]{};
					if ( dispType.equals("combobox") )
					{
//						//OCX版
//						comboboxlist = FormPropertyUtils.getPropComboList(null,showstate.toString(),map);	
						
						//WPE版
						comboboxlist = (List<String>) tag_attr_map.get("combobox");
						
						if ( comboboxlist != null )
							comboboxs = (String[])comboboxlist.toArray(new String[]{});				
					}			
					
					PropertyDescriptor propDes = null;
					if ( dispType.equals("combobox") )
						//第一个参数是下面的id值来源
						propDes = new ComboBoxPropertyDescriptor(name,caption,comboboxs);
					else if ( dispType.equals("dialog") )
					{
//						map.put(FormConst.FIXMAINEDITOR_INSTANCE, this.mainEditor);
//						map.put(FormConst.FIXCOMPONENT_ID, selectNode.id);
//						map.put(FormConst.FIXCOMPONENTSKEY, selectNode.name);
						
						propDes = new WPECustomDialogPropertyDescriptor(
								tag_attr_map,propertyJsons[i]);
					}
					else if ( descriptorsCount == 0 )
					{
						propDes = new PropertyDescriptor(FormConst.FIXCOMPONENTSKEY,FormConst.FIXCOMPONENTSKEYCN);
					}
					else if ( readOnly )
					{
						propDes = new PropertyDescriptor(name,caption);
					}
					else 
						propDes = new TextPropertyDescriptor(name,caption);
					
					Object description = tag_attr_map.get("description");
					if ( description != null )
					{
						propDes.setDescription(description.toString());
					}
					propDes.setCategory(category);
					//propDes.
					descriptors[descriptorsCount] = propDes;
					
					
					
//					descriptors[descriptorsCount] = new TextPropertyDescriptor(
//							stringTemp,stringTemp);
//					
//					//这里需要知道是attribute还是even
//					if(propetyType.equals(ConstantProperty.propetyTypeAttribute)){
//						descriptors[descriptorsCount].setCategory(ConstantProperty.attributeCategroy[i]);
//					}else{
//						descriptors[descriptorsCount].setCategory(ConstantProperty.evenCategroy[i]);
//					}
					//这个放最后
					descriptorsCount ++;
				}
			}
		}
		return descriptors;
	}

	public Object getPropertyValue(Object id) {
		
		
		/*
		 *	@author Fifteenth
		 *		key是组件的某个属性
		 */
		String key = id.toString();
		/*
		 *	@author Fifteenth
		 *		tag_ttr_map是组件的某个属性的map
		 */
		Map<String,Object> tag_ttr_map = AbstractTagCreatorProvider.getXMLProperty(
				proImpl.getAttribute("ComponentType"), key);
		
		
		for(int i=0;i<propertyJsons.length;i++){
			try {
				
				/*
				 *	@author Fifteenth
				 *		需要将key值翻译一下
				 */
				if(_chanslateMap.get(key)!=null){
					String tempKey = _chanslateMap.get(key).toString();
					if(tag_ttr_map!=null&&propertyJsons[i].has(tempKey)){
						Object valueObject = propertyJsons[i].get(tempKey);
						
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
							if(1==1){
								//弹出
								return valueObject;
							}else{
								childPropertyJsons[i] = (JSONObject)valueObject;
								//子属性页
								FixSubAttributePropertySource fixSubPropertySourc = 
										new FixSubAttributePropertySource(this,id.toString(),i);
								return fixSubPropertySourc;
							}
						}else{
							
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
		 * 		当修改子节点属性的时候
		 * 	id不为null，value
		 */
		if(id!=null && value==null
				&& childSaveMark
				){
			for(int i=0;i<childKeys.length;i++){
				if(childPropertyJsons[i]!=null){
					childSaveMark = false;
					childKeys[i] =  id.toString();
					try {
						propertyJsons[i].put(childKeys[i], childPropertyJsons[i]);
						setNodeValueAndReflesh(id.toString(),
								propertyJsons[i].get(id.toString()));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		if(value!=null && 
				(!id.equals(saveId) || !value.equals(saveValue))){
			if(value!=null){
				for(int i=0;i<propertyJsons.length;i++){
				String stringTemp = id.toString();
				if(propertyJsons[i]!=null){
						if(propertyJsons[i].has(stringTemp)){
							// 区分是attribute还是even....
							if(propetyType.equals(ConstantProperty.propetyTypeAttribute)){
								/*
								 * @author Fifteenth
								 * 		i=0:tag的属性
								 * 		i=1:model的属性
								 */
								if(i==0){
									String category = ConstantProperty.attributeCategroy[i];
									saveValue = value;
									saveId = id;
									impl.setAttribute(stringTemp, value.toString());
									provider.refleshTagProperty();
								}else if(i==1){
									setNodeValueAndReflesh(id.toString(),value.toString());
								}
							}else if(propetyType.equals(ConstantProperty.propetyTypeEven)){
								
							}
						}
					}
				}
			}
		}
	}


	/**
	 * 
	 */
	public int getDescriptorLength(){
		int descriptorLength = 0;
		for(int i=0;i<propertyJsons.length;i++){
			if(propertyJsons[i]!=null){
				descriptorLength += propertyJsons[i].length();
			}
		}
		return descriptorLength;
	}
	
	
	
	
	public void setNodeValueAndReflesh(String idString,Object value)
	{
		/*
		 * @author Fifteenth
		 * 		遍历所有modelName
		 * 			目前就只有@FixAttributies:
		 */
		for(int ii=0;ii<ConstantProperty.attributeCategroyModelName.length;
				ii++){
			String modelName = ConstantProperty.attributeCategroyModelName[ii];
			NodeList list = impl.getChildNodes();
			/*
			 * @author Fifteenth
			 * 		得到对应modelName的json
			 */
			if(ModelCommet.getJson(
					list, propetyType, modelName
					).has(idString)){
				/*
				 * @author Fifteenth
				 * 		得到对应modelName的节点
				 */
				Node node = ModelCommet.getNode(
						list, propetyType, 
						modelName);
				
				/*
				 * @author Fifteenth
				 * 通过修改节点内容，设值
				 */
				String commentString = node.getTextContent().trim();
				
				String jsonString = commentString.substring(modelName.length(),
						commentString.length()); //$NON-NLS-1$
//				JsonParser jp = new JsonParser();
//				JsonObject json = (JsonObject)jp.parse(jsonString); 
				
				JSONObject json;
				try {
					json = new JSONObject(jsonString);
					if(json.has(idString)){
						json.put(idString, value);
					}
					saveValue = value;
					saveId = idString;
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
	}
	
	
	public Object ocxValue(Object valueObject,Map tag_ttr_map){
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
