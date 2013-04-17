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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Fifteenth
 */
public class FixEvenPropertySourceProvider implements IPropertySourceProvider {

	/**
	 * Constructor
	 */
	public FixEvenPropertySourceProvider(){
		
	}
	
	
	private FixEvenPropertySource _evenSource;
	
	
	private JSONObject _evenJson;


	public JSONObject get_evenJson() {
		return _evenJson;
	}


	@SuppressWarnings("restriction")
	public IPropertySource getPropertySource(Object object) {
		
		
		/*
		//you can see that the object only instantiated once 
		System.out.println(this.hashCode());*/
		
		System.out.println(object.hashCode());
	
		if(!(object instanceof ElementStyleImpl)){
			return null;
		}
		
		
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
				if(commentString.contains("@FixEvens:")){ //$NON-NLS-1$
					//FixAttributies
					String jsonString = commentString.substring(
							"@FixEvens:".length(), commentString.length()); //$NON-NLS-1$
					try {
						_evenJson = new JSONObject(jsonString);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
					//修改注释
//					node.setNodeValue("aaaaaaaaaa"); //$NON-NLS-1$
				}
			}
		}
		
		//one tag should be only instantiated once
		_evenSource = new FixEvenPropertySource(this);
		
		return _evenSource;
	}
}
