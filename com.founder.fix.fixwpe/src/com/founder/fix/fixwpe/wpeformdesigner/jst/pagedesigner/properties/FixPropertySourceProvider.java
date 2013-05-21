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
package com.founder.fix.fixwpe.wpeformdesigner.jst.pagedesigner.properties;

import java.util.Map;

import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.eclipse.wst.html.core.internal.document.ElementStyleImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.founder.fix.studio.wpeformdesigner.wst.html.core.internal.document.FixElementStyleImpl;
import com.founder.fix.studio.wpeformdesigner.jst.pagedesigner.itemcreation.AbstractTagCreatorProvider;
import com.founder.fix.studio.wpeformdesigner.jst.pagedesigner.itemcreation.XmlPropBufferProvider;
import com.founder.fix.studio.wpeformdesigner.jst.pagedesigner.properties.FixPropertySourceProvider;


/**
 * @author Fifteenth
 */
public class FixPropertySourceProvider implements IPropertySourceProvider {

	
	public String _propetyType;
	
	private ElementStyleImpl impl;
	
	/*
	 *	@author Fifteenth
	 *		1.impl:控件模型(model)
	 *		2.fixImpl：提供数据的控件模型，用于改变模型
	 */
	private FixElementStyleImpl fixImpl;
	private ElementStyleImpl implOld;
	/**
	 * Constructor
	 */
	public FixPropertySourceProvider(String propetyType){
		
		this._propetyType = propetyType;
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
			fixImpl = new FixElementStyleImpl(impl);
		}else{
			//这个地方还需仔细测试
			return fixImpl.getPropertySource();
		}
		
		implOld = impl;
		
		
		// 得到Tag数据
		refleshTagProperty();
		
		// 得到Model数据
		refleshModelProperty();
		
		
		
		if(fixImpl.getPropertySource()==null){
			/*
			 *	@author Fifteenth
			 *		1.选中一个组件时创建一个FixPropertySource对象
			 *	当修改属性时会找到该对象
			 *
			 *		2.没能实现一个组件一直使用同一个FixPropertySource
			 *	对象，当切换回该组件时重新创建，而不是去找之前的
			 */
			fixImpl.setPropertySource(new FixPropertySource(
					this,_propetyType,fixImpl,impl));
		}
		
		/*
		 *	@author Fifteenth
		 *		触发属性
		 */
		return fixImpl.getPropertySource();
	}
	
	/**
	 *	刷新model属性
	 */
	public void refleshModelProperty(){
		NodeList nodeList = implOld.getChildNodes();
		
		try{
			JSONObject attributeJsonModel = ModelCommet.getJson(
					nodeList, _propetyType, 
					ConstantVariable.fixAttributeNameSpace);
			/*
			 *	@author Fifteenth
			 *		model
			 */
			if(fixImpl.getObjectJson()==null){
				fixImpl.setObjectJson(new JSONObject());
				fixImpl.getObjectJson().put(
						ConstantVariable.childJsonAttributeModel, 
						attributeJsonModel);
			}else{
				fixImpl.getObjectJson().put(
						ConstantVariable.childJsonAttributeModel, 
						attributeJsonModel);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *	刷新tag属性
	 */
	public void refleshTagProperty(){
		NamedNodeMap attributes = impl.getAttributes();
		// 第二部分：得到组件对象子元素
		JSONObject attributeJsonTag = new JSONObject();
		for(int i=0;i<attributes.getLength();i++){
			try {
				attributeJsonTag.put(attributes.item(i).getNodeName(),
						attributes.item(i).getNodeValue());
				
				if(fixImpl.getObjectJson()==null){
					fixImpl.setObjectJson(new JSONObject());
					fixImpl.getObjectJson().put(
							ConstantVariable.childJsonAttributeTag, attributeJsonTag);
				}else{
					fixImpl.getObjectJson().put(
							ConstantVariable.childJsonAttributeTag, attributeJsonTag);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public static Map getTagAttrMap(FixElementStyleImpl proImpl,String key_en){
		Map<String,Object> tag_ttr_map;
		
		if(proImpl.getNodeName().equals(AbstractTagCreatorProvider.nodeName_INPUT)){
			tag_ttr_map = XmlPropBufferProvider.getXMLProperty(
					"input|text", key_en);
		}else{
			tag_ttr_map = XmlPropBufferProvider.getXMLProperty(
					proImpl.getAttribute("ComponentType"), key_en);
		}
		
		return tag_ttr_map;
	}
}
