package com.founder.fix.jst.pagedesigner.properties;


import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.wst.html.core.internal.document.ElementStyleImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.founder.fix.util.JSonUtil;
import com.founder.fix.wst.html.core.internal.document.PropertyElementStyleImpl;


/**
 * @author Fifteenth
 */

public class FixPropertySource implements IPropertySource {
	
	private FixPropertySourceProvider provider;

	private PropertyElementStyleImpl proImpl;
	private ElementStyleImpl impl;
	
	private JSONObject[] propertyJson;
	
	private String propetyType;
	
	private Object SaveValue;
	
	
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
			propertyJson = new JSONObject[ConstantProperty.attributeJson.length];
			for(int i=0;i<ConstantProperty.attributeJson.length;i++){
				if(ConstantProperty.childJsonAttribute[i].equals(
						ConstantProperty.childJsonAttributeModel)){
					for(int ii=0;ii<ConstantProperty.attributeCategroyModelName.length;
							ii++){
						propertyJson[i] = JSonUtil.getChildJosn(
								proImpl.getObjectJson(),ConstantProperty.attributeCategroyModelName[ii]);
					}
				}else{
					propertyJson[i] = JSonUtil.getChildJosn(
							proImpl.getObjectJson(),ConstantProperty.childJsonAttribute[i]);
				}
				
			}
		}else if(propetyType.equals(ConstantProperty.propetyTypeEven)){
			propertyJson = new JSONObject[ConstantProperty.evenJson.length];
			for(int i=0;i<ConstantProperty.evenJson.length;i++){
				propertyJson[i] = JSonUtil.getChildJosn(
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
		for(int i=0;i<propertyJson.length;i++){
			if(propertyJson[i]!=null){
				Iterator jsonKeys = propertyJson[i].keys();
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
		
		for(int i=0;i<propertyJson.length;i++){
			try {
				String stringTemp = id.toString();
				if(propertyJson[i].has(stringTemp)){
					final Object valueObject = propertyJson[i].get(stringTemp);
					if(valueObject instanceof JSONObject){
						/*
						 *	@author Fifteenth
						 *		1. 根据配置子属性页或者弹出
						 * 
						 * 		2. 需要在这里触发调用子属性页
						 */
						if(1==2){
							//弹出
							return valueObject;
						}else{
							//子属性页
							FixSubAttributePropertySource fixSubPropertySourc = 
									new FixSubAttributePropertySource((JSONObject)valueObject);
							return fixSubPropertySourc;
						}
					}else{
						return propertyJson[i].get(id.toString());
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

	public void setPropertyValue(Object id, Object value) {
		if(value!=null&&!value.equals(SaveValue)){
			for(int i=0;i<propertyJson.length;i++){
				String stringTemp = id.toString();
				if(propertyJson[i]!=null){
					if(propertyJson[i].has(stringTemp)){
						// 区分是attribute还是even....
						if(propetyType.equals(ConstantProperty.propetyTypeAttribute)){
							if(i==0){
								String category = ConstantProperty.attributeCategroy[i];
								SaveValue = value;
								impl.setAttribute(stringTemp, value.toString());
								provider.refleshTagProperty();
							}else if(i==1){
								//遍历
								for(int ii=0;ii<ConstantProperty.attributeCategroyModelName.length;
										ii++){
									String modelName = ConstantProperty.attributeCategroyModelName[ii];
									NodeList list = impl.getChildNodes();
									if(ModelCommet.getJson(
											list, propetyType, modelName
											).has(id.toString())){
										
										Node node = ModelCommet.getNode(
												list, propetyType, 
												modelName);
										
										String commentString = node.getTextContent().trim();
										
										String jsonString = commentString.substring(modelName.length(),
												commentString.length()); //$NON-NLS-1$
										try {
											JSONObject json = new JSONObject(jsonString);
											String key = id.toString();
											if(json.has(key)){
												json.put(key, value.toString());
											}
											SaveValue = value;
											node.setNodeValue(modelName+json.toString());
											provider.refleshModelProperty();
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} 
									}
								}
							}
						}else if(propetyType.equals(ConstantProperty.propetyTypeEven)){
							
						}
					}
				}
			}
		}
	}

	
	public int getDescriptorLength(){
		int descriptorLength = 0;
		for(int i=0;i<propertyJson.length;i++){
			if(propertyJson[i]!=null){
				descriptorLength += propertyJson[i].length();
			}
		}
		return descriptorLength;
	}
}
