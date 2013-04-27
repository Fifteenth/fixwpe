/*******************************************************************************
 * Copyright (c) 2006 Sybase, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sybase, Inc. - initial API and implementation
 *******************************************************************************/
package com.founder.fix.jst.pagedesigner.properties;

import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.eclipse.wst.html.core.internal.document.ElementStyleImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.founder.fix.wst.html.core.internal.document.PropertyElementStyleImpl;


/**
 * @author Fifteenth
 */
public class FixPropertySourceProvider implements IPropertySourceProvider {

	
	public String propetyType;
	
	private ElementStyleImpl impl;
	
	private PropertyElementStyleImpl proImpl;
	private ElementStyleImpl implOld;
	/**
	 * Constructor
	 */
	public FixPropertySourceProvider(String propetyType){
		
		this.propetyType = propetyType;
	}
	
	@SuppressWarnings("restriction")
	public IPropertySource getPropertySource(Object object) {
		/*
		 *	@author Fifteenth
		 *		1.object是getPropertyValue返回的对象，除了需要再调用
		 *	子属性以外，其它的return null
		 *		
		 *		2.返回类型一个是null，一个是IPropertySource对象
		 */
		
		if(!(object instanceof ElementStyleImpl)){
			if(object instanceof FixSubAttributePropertySource){
				/*
				 * @author Fifteenth
				 *		触发子属性
				 */
				return (FixSubAttributePropertySource)object;
			}else{
				return null;
			}
		}
		
		impl = (ElementStyleImpl) object;
		
		if(!impl.equals(implOld)){
			proImpl = new PropertyElementStyleImpl(impl);
		}else{
			//这个地方还需仔细测试
			return proImpl.getPropertySource();
		}
		
		implOld = impl;
		
		// 第一部分：过滤得到组件对象的子元素
		refleshModelProperty();
		
		if(propetyType.equals(ConstantProperty.propetyTypeAttribute)){
			refleshTagProperty();
		}
		
		if(proImpl.getPropertySource()==null){
			/*
			 *	@author Fifteenth
			 *		1.选中一个组件时创建一个FixPropertySource对象
			 *	当修改属性时会找到该对象
			 *
			 *		2.没能实现一个组件一直使用同一个FixPropertySource
			 *	对象，当切换回该组件时重新创建，而不是去找之前的
			 */
			proImpl.setPropertySource(new FixPropertySource(
					this,proImpl,impl,propetyType));
		}
		
		/*
		 *	@author Fifteenth
		 *		触发属性
		 */
		return proImpl.getPropertySource();
	}
	
	/**
	 *	刷新model属性
	 */
	public void refleshModelProperty(){
		NodeList nodeList = implOld.getChildNodes();
		
		try{
			if(propetyType.equals(ConstantProperty.propetyTypeAttribute)){
				
				for(int i=0;i<ConstantProperty.attributeCategroyModelName.length;
						i++){
					
					JSONObject attributeJsonModel = ModelCommet.getJson(
							nodeList, propetyType, 
							ConstantProperty.attributeCategroyModelName[i]);
					if(proImpl.getObjectJson()==null){
						proImpl.setObjectJson(new JSONObject());
						proImpl.getObjectJson().put(
								ConstantProperty.attributeCategroyModelName[i], 
								attributeJsonModel);
					}else{
						proImpl.getObjectJson().put(
								ConstantProperty.attributeCategroyModelName[i], 
								attributeJsonModel);
					}
					
				}
			}else if(propetyType.equals(ConstantProperty.propetyTypeEven)){
				
				for(int i=0;i<ConstantProperty.evenCategroyModelName.length;
						i++){
					JSONObject evenJsonModel = ModelCommet.getJson(
							nodeList, propetyType, 
							ConstantProperty.evenCategroyModelName[i]);
					if(proImpl.getObjectJson()==null){
						proImpl.setObjectJson(new JSONObject());
						proImpl.getObjectJson().put(
								ConstantProperty.evenCategroyModelName[i], 
								evenJsonModel);
					}else{
						proImpl.getObjectJson().put(
								ConstantProperty.evenCategroyModelName[i], 
								evenJsonModel);
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *	刷新tab属性
	 */
	public void refleshTagProperty(){
		NamedNodeMap attributes = impl.getAttributes();
		// 第二部分：得到组件对象子元素
		JSONObject attributeJsonTag = new JSONObject();
		for(int i=0;i<attributes.getLength();i++){
			try {
				attributeJsonTag.put(attributes.item(i).getNodeName(),
						attributes.item(i).getNodeValue());
				
				if(proImpl.getObjectJson()==null){
					proImpl.setObjectJson(new JSONObject());
					proImpl.getObjectJson().put(
							ConstantProperty.childJsonAttributeTag, attributeJsonTag);
				}else{
					proImpl.getObjectJson().put(
							ConstantProperty.childJsonAttributeTag, attributeJsonTag);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
