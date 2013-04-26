package com.founder.fix.jst.pagedesigner.properties;


import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.wst.html.core.internal.document.ElementStyleImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	
	
	
	// 刷出属性
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
		
		
//		System.out.println(this.hashCode());
		
		refleshPropertyJosn(propetyType);
		
		
		PropertyDescriptor[] descriptors = new PropertyDescriptor[getDescriptorLength()];	
		int descriptorsCount = 0;
		
		// 不需要知道是attribute还是even
		for(int i=0;i<propertyJsons.length;i++){
			if(propertyJsons[i]!=null){
				Iterator jsonKeys = propertyJsons[i].keys();
				while(jsonKeys.hasNext()){
					String stringTemp = jsonKeys.next().toString();
					descriptors[descriptorsCount] = new TextPropertyDescriptor(
							stringTemp,stringTemp);
					
					//这里需要知道是attribute还是even
					if(propetyType.equals(ConstantProperty.propetyTypeAttribute)){
						descriptors[descriptorsCount].setCategory(ConstantProperty.attributeCategroy[i]);
					}else{
						descriptors[descriptorsCount].setCategory(ConstantProperty.evenCategroy[i]);
					}
					//这个放最后
					descriptorsCount ++;
				}
			}
		}
		return descriptors;
	}

	public Object getPropertyValue(Object id) {
		
		for(int i=0;i<propertyJsons.length;i++){
			try {
				if(id.toString().equals("配置数据")){
					System.out.println();
				}
				
				String stringTemp = id.toString();
				if(propertyJsons[i].has(stringTemp)){
					Object valueObject = propertyJsons[i].get(stringTemp);
					
					if(valueObject instanceof JSONObject){
						/*
						 *	@author Fifteenth
						 *		1. 根据配置子属性页或者弹出
						 * 
						 * 		2. 需要在这里触发调用子属性页
						 */
						String json = "{\"name\":\"value\"}";
				        String t = json.replaceAll("\"(\\w+)\"(\\s*:\\s*)", "$1$2");
						
						String jsonString = (valueObject.toString()).replaceAll("\"(\\w+)\"(\\s*:\\s*)", "$1$2");
						valueObject = new JSONObject(jsonString);
						if(1==2){
							//弹出
							return valueObject;
						}else{
							childPropertyJsons[i] = (JSONObject)valueObject;
							//子属性页
							FixSubAttributePropertySource fixSubPropertySourc = 
									new FixSubAttributePropertySource(this,i);
							return fixSubPropertySourc;
						}
					}else{
						return propertyJsons[i].get(id.toString());
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
}
