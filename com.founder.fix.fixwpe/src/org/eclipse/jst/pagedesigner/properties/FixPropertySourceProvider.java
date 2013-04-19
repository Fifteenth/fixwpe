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
package org.eclipse.jst.pagedesigner.properties;

import org.eclipse.ConstantProperty;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.eclipse.wst.html.core.internal.document.ElementStyleImpl;
import org.eclipse.wst.html.core.internal.document.PropertyElementStyleImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Fifteenth
 */
public class FixPropertySourceProvider implements IPropertySourceProvider {

	
	public String propetyType;
	
	ElementStyleImpl impl;
	
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
		if(!(object instanceof ElementStyleImpl)){
			//
			if(object instanceof FixSubAttributePropertySource){
				// 需要在这里触发调用子属性页
				return (FixSubAttributePropertySource)object;
			}else{
				return null;
			}
		}
		
		impl = (ElementStyleImpl) object;
		if(!impl.equals(implOld)){
			//一个组件只实例化一次，目前没有做到
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
			proImpl.setPropertySource(new FixPropertySource(this,proImpl,impl,propetyType));
		}
		
		return proImpl.getPropertySource();
	}
	
	
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
