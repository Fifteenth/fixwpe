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

import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.eclipse.wst.html.core.internal.document.ElementStyleImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author mengbo
 */
public class FixAttributePropertySourceProvider implements IPropertySourceProvider {

	/**
	 * Constructor
	 */
	
	/*
	 * founderfix
	 * 代码说明
	 * 
	 * 控制重复调用代码
	 */
	private Object _oldObject;
	
	
	/*
	 * fouderfix
	 * 代码说明
	 * 
	 * 每个组件一个subPropertySource
	 */
	private FixAttributePropertySource _attributeSource;
	
	
	
	/*
	 * fouderfix
	 * 代码说明
	 * 
	 */
	private JSONObject _attributeJsonTag;
	private JSONObject _attributeJsonModel;
	
	
	public JSONObject get_propertyJsonTag() {
		return _attributeJsonTag;
	}

	public JSONObject get_propertyJsonModel() {
		return _attributeJsonModel;
	}


	public IPropertySource getPropertySource(Object object) {
	
		if(!(object instanceof ElementStyleImpl)){
			//
			if(object instanceof FixSubAttributePropertySource){
				/*
				 * 子属性页
				 */
				return (FixSubAttributePropertySource)object;
			}else{
				return null;
			}
		}
		
		if(object!=null
				&&object.equals(_oldObject)){
			//here subPropertySource should never null
			return _attributeSource;
		}
		
		_oldObject = object;
		
		ElementStyleImpl elementStyleImpl = (ElementStyleImpl)object;
		NodeList nodeList = elementStyleImpl.getChildNodes();
		
		
		
		for(int i=0;i<nodeList.getLength();i++){
			
			Node node = nodeList.item(i);
			if(node.getNodeName().equals("#comment")){ //$NON-NLS-1$
				String commentString = node.getTextContent().trim();
				if(commentString.contains("@FixNamespace:")){ //$NON-NLS-1$
					//命名空间
//					String jsonString = commentString.substring(
//							"@FixNamespace:".length(), commentString.length()); //$NON-NLS-1$
					//修改注释
//					node.setNodeValue("aaaaaaaaaa"); //$NON-NLS-1$
				}
				if(commentString.contains("@FixAttributies:")){ //$NON-NLS-1$
					//FixAttributies
					String jsonString = commentString.substring(
							"@FixAttributies:".length(), commentString.length()); //$NON-NLS-1$
					try {
						_attributeJsonModel = new JSONObject(jsonString);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
//					node.setNodeValue("aaaaaaaaaa"); //$NON-NLS-1$
				}
			}
		}
		
		
		NamedNodeMap attributes = elementStyleImpl.getAttributes();
		
		_attributeJsonTag = new JSONObject();
		for(int i=0;i<attributes.getLength();i++){
			try {
				_attributeJsonTag.put(attributes.item(i).getNodeName(),
						attributes.item(i).getNodeValue());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		_attributeSource = new FixAttributePropertySource(this);
		return _attributeSource;
	}
}
