package com.founder.fix.jst.pagedesigner.properties;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * @author Fifteenth
 */
public class ModelCommet {
	
	
	
	
	public ModelCommet(){
		
	}
	
	/*
	 * ConstantProperty.propetyTypeAttribute
	 * ConstantProperty.attributeCategroyModelNameAttribute
	 * 
	 */
	public static JSONObject getJson(NodeList nodeList,
			String propetyType,String modelName){
		
		JSONObject attributeJsonModel = null;
		
		for(int i=0;i<nodeList.getLength();i++){
			
			Node node = nodeList.item(i);
			
			if(node.getNodeName().equals(ConstantProperty.propertyComment)){ //$NON-NLS-1$
				String commentString = node.getTextContent().trim();
				
				if(propetyType.equals(propetyType)){
					if(commentString.contains(modelName)){ //$NON-NLS-1$
						// FixAttributies
						String jsonString = commentString.substring(modelName.length(),
								commentString.length()); //$NON-NLS-1$
						try {
							attributeJsonModel = new JSONObject(jsonString);
							return attributeJsonModel;
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
					}
				}
			}
		}
		return null;
	}
	
	
	public static Node getNode(NodeList nodeList,
			String propetyType,String modelName){
		
		for(int i=0;i<nodeList.getLength();i++){
			
			Node node = nodeList.item(i);
			
			// 处理过滤出来的(注释)子元素
			if(node.getNodeName().equals(ConstantProperty.propertyComment)){ //$NON-NLS-1$
				String commentString = node.getTextContent().trim();
				
				if(commentString.contains(modelName)){ //$NON-NLS-1$
					// FixAttributies
					return node; 
				}
			}
		}
		return null;
	}
}
