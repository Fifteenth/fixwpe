package com.founder.fix.fixwpe.wpeformdesigner.jst.pagedesigner.properties;

import org.eclipse.wst.html.core.internal.document.ElementStyleImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.founder.fix.base.wpe.ConstantVariable;


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
			
			if(node.getNodeName().equals(ConstantVariable.propertyComment)){ //$NON-NLS-1$
				String commentString = node.getTextContent().trim();
				
				if(propetyType.equals(propetyType)){
					if(commentString.contains(modelName)){ 
						// FixAttributies
						String jsonString = commentString.substring(commentString.indexOf(":")+":".length(),
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
	
	
	public static Node getNode(ElementStyleImpl impl,String modelName){
		
		NodeList nodeList = impl.getChildNodes();
		
		for(int i=0;i<nodeList.getLength();i++){
			
			Node childNode = nodeList.item(i);
			
			// 处理过滤出来的(注释)子元素
			if(childNode.getNodeName().equals(ConstantVariable.propertyComment)){ //$NON-NLS-1$
//				String commentString = node.getTextContent().trim();
				
//				if(commentString.contains(modelName)){ //$NON-NLS-1$
					// FixAttributies
					return childNode; 
//				}
			}
		}
		return null;
	}
	
	
	public static JSONObject getCommentJson(Node commentNode){
		if(commentNode!=null){
			String commentString = commentNode.getTextContent().trim();
//			String modelName = ConstantVariable.fixAttributeNameSpace;
			
			String jsonString = commentString.substring(
					commentString.indexOf(":")+":".length(),
					commentString.length()); //$NON-NLS-1$
			
			JSONObject json;
			try {
				json = new JSONObject(jsonString);
				return json;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
//	public static String getFieldText(Node node){
//		String key = "字段";
//		
//		return null;
//	}
}
