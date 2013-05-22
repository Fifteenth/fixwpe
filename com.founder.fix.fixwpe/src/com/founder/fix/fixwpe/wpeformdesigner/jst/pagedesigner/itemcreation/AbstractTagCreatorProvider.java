package com.founder.fix.fixwpe.wpeformdesigner.jst.pagedesigner.itemcreation;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.eclipse.wst.xml.core.internal.document.AttrImpl;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.founder.fix.apputil.to.bizobj.BizObjTo;
import com.founder.fix.apputil.to.bizobj.ObjColumnTo;
import com.founder.fix.fixwpe.wpeformdesigner.XmlPropBufferProvider;
import com.founder.fix.ocx.util.ComparatorUtil;
import com.founder.fix.ocx.util.FixUtil;

/**
 * @author Fifteenth
 */
public class AbstractTagCreatorProvider {
	
	
	//nodeName
	public static String nodeName_HTML = "html";
	public static String nodeName_HEAD = "head";
	public static String nodeName_TR = "tr";
	public static String nodeName_TD = "td";
	public static String nodeName_INPUT = "input";
	
	public static String nodeName_SCRIPT = "script";
	public static String nodeName_LINK = "link";

	public static String nodeName_SPAN = "span";
	
	
	//tagAttr
	public static String tagAttr_ComponentType = "ComponentType";
	public static String tagAttr_ID = "id";
	public static String tagAttr_TYPE = "type";
	public static String tagAttr_REFNUM = "refNum";
	public static String tagAttr_INPUT = "INPUT.TEXT";
	
	public static String tagAttr_SRC = "src";
	
	public static String tagAttr_REL = "rel";
	public static String tagAttr_HREF = "href";
	
	public static String tagAttrVlue_INPUT = "INPUT|TEXT";
	
	//refType
	public static String jsRef = "jsref";
	public static String cssRef = "cssref";	
	
	/*
	 *	@author Fifteenth
	 *		得到节点的指定父节点
	 */
	public static IDOMNode getPointParentNode(IDOMNode containerNode,String nodeName){
		while(!containerNode.getParentNode().getNodeName().equals(nodeName)){
			containerNode = (IDOMNode)containerNode.getParentNode();
    		if(containerNode==null){
    			/*
    			 * 	@author Fifteenth
    			 * 		没有找到html标签，属异常情况
    			 */
    			System.out.println("warning!!!!!!!!!!!!!!!"); //$NON-NLS-1$
    			return null;
    		}
    	}
		return (IDOMNode) containerNode.getParentNode();
	}
	
	
	/*
	 *	@author Fifteenth
	 *		得到节点的指定子节点
	 */
	public static Node getPointChildNode(Node node,String nodeName){
		int length = node.getChildNodes().getLength();
		for(int i=0;i<length;i++){
    		// 得到head标记对中的节点
			Node pointChildNode = node.getChildNodes().item(i);
    		if(pointChildNode.getNodeName().
    				equals(nodeName)){
    			return pointChildNode;
    		}
		}
		return null;
	}
	
	public static String getDetailAutoAttrValue(Node node,String componentType,int _i){
		String idValue = ""; 
    	List<String> idList = new ArrayList();
    	String autoValue = componentType;
    	String tagName;
		
		autoValue = "detail_"+nodeName_INPUT;
		tagName = nodeName_INPUT;
		NodeList nodeList = node.getOwnerDocument().
				getElementsByTagName(tagName);
		for(int i=0;i<nodeList.getLength();i++){
			nodeList.item(i).getAttributes().getLength();			
			// componentType：得到组件类型（比如：ztree、）
			AttrImpl attrImpl_ComponentType = (AttrImpl)nodeList.item(i).getAttributes().
					getNamedItem(tagAttr_ComponentType);
			String tag_componentType = null;
			if(attrImpl_ComponentType!=null){
				tag_componentType = attrImpl_ComponentType.
						getNodeValue();
			}
			
			// attr
			AttrImpl attrImpl_ID = (AttrImpl)nodeList.item(i).getAttributes().
					getNamedItem(tagAttr_ID);
			
			if(attrImpl_ID!=null
					&&tag_componentType!=null
					&&componentType.equals(tag_componentType)){
				// 只放入同类型的组件
				String valueString = attrImpl_ID.getValue().toString();
				valueString = valueString.substring(0, valueString.lastIndexOf("_"));
				if(!idList.contains(valueString)){
					idList.add(valueString);
				}
			}
		}
		
		
		if(idList.size()==0){
			idValue = autoValue+"_0"+"_"+_i; //$NON-NLS-1$
		}else{
			// idList排序
    		ComparatorUtil comparator = new ComparatorUtil();
    		Collections.sort(idList, comparator);
    		
        	for(int i=0;i<idList.size();i++){
        		String tempString = autoValue+"_"+i;//$NON-NLS-1$
        		if(!(idList.get(i).contains(tempString))){ 
        			if(!idList.contains(tempString)){
        				idValue = tempString+"_"+_i;
        				break;
        			}
        		}
        	}
        	if(idValue.equals("")){ //$NON-NLS-1$
        		idValue = autoValue+"_"+idList.size()+"_"+_i; //$NON-NLS-1$
        	}
		}
		return idValue;
	}
	
	public static String getAutoAttrValue(Node node,String componentType){
		String idValue = ""; 
    	List<String> idList = new ArrayList();
    	String autoValue = componentType;
    	if(componentType.endsWith(tagAttr_INPUT)){
    		autoValue = nodeName_INPUT;
    	}
    	
    	String tagName = nodeName_SPAN;
		NodeList nodeList = node.getOwnerDocument().
				getElementsByTagName(tagName);
		for(int i=0;i<nodeList.getLength();i++){
			nodeList.item(i).getAttributes().getLength();
			// componentType：得到组件类型（比如：ztree、）
			String tag_componentType = nodeList.item(i).getAttributes().
					getNamedItem(tagAttr_ComponentType).
					getNodeValue();
			// attr
			AttrImpl attrImpl = (AttrImpl)nodeList.item(i).getAttributes().
					getNamedItem(tagAttr_ID);
			
			if(attrImpl!=null
					&&componentType.equals(tag_componentType)){
				// 只放入同类型的组件
				idList.add(attrImpl.getValue().toString());
			}
		}
		
		
		if(idList.size()==0){
			idValue = autoValue+"_0"; //$NON-NLS-1$
		}else{
			// idList排序
    		ComparatorUtil comparator = new ComparatorUtil();
    		Collections.sort(idList, comparator);
    		
        	for(int i=0;i<idList.size();i++){
        		String tempString = autoValue+"_"+i;//$NON-NLS-1$
        		if(!(idList.get(i).equals(tempString))){ 
        			if(!idList.contains(tempString)){
        				idValue = tempString;
        				break;
        			}
        		}
        	}
        	if(idValue.equals("")){ //$NON-NLS-1$
        		idValue = autoValue+"_"+idList.size(); //$NON-NLS-1$
        	}
		}
		return idValue;
	}
	
	/*
	 *	@author Fifteenth
	 *		生成明细表
	 */
	public static void createDetailTalbe(int colCount,int rowCount,
			String bizObjName,IDOMDocument  domDocument,
			Element ele,Node htmlNode,Boolean aISelectionState){
		
		BizObjTo bizObj = FixUtil.getBizObjById(bizObjName);
		Boolean isAI = false;
		if(bizObj!=null){
			if(aISelectionState){
				isAI = true;
			}
		}
		for(int i=0;i<rowCount;i++){
			/*
			 *	@author Fifteenth
			 *		1.1=0:绑定字段名
			 *		2.i=1:绑定文本
			 */
			
			Element trElement = domDocument.
					createElement(nodeName_TR);
			for(int j=0;j<colCount;j++){
				if(i==0){
					Element tdElement = domDocument.
							createElement(nodeName_TD);
					trElement.appendChild(tdElement);
					
					//智能
					if(isAI){ 
						IDOMNode commentEle = (IDOMNode) domDocument.createTextNode(bizObj.getColumns().get(j).getName());
						tdElement.appendChild(commentEle);
					}
				}else if(i==1){
					// td
					Element tdElement = domDocument.
							createElement(nodeName_TD);
					
					//智能
					if(isAI){ //$NON-NLS-1$
						// td-text
						Element textElement = domDocument.
    							createElement(nodeName_INPUT);
						// td-text->attr1(ComponentType)
						textElement.setAttribute(
								tagAttr_ComponentType, 
								tagAttr_INPUT);
						// td-text->attr2(id)
						String nodeId = getDetailAutoAttrValue(
								htmlNode, 
								tagAttr_INPUT, j);
						textElement.setAttribute(
								tagAttr_ID, nodeId);
						// td-text->attrcomment
						IDOMNode coment = getDetailInputComentNode(tagAttrVlue_INPUT,
								domDocument,bizObj.getColumns().get(j),bizObjName,nodeId);
						textElement.appendChild(coment);
						
						tdElement.appendChild(textElement);
    					trElement.appendChild(tdElement);
					}
				}else{
					Element tdElement = domDocument.
							createElement(nodeName_TD);
					trElement.appendChild(tdElement);
				}
			}
			ele.appendChild(trElement);
		}
	}
	
	
	/*
	 *	@author Fifteenth
	 *		注释
	 */
	public static IDOMNode getComentNode(String componentType,IDOMDocument domDocument,String nodeId){
		boolean isDetailInputComment = false;
		String commentValue = XmlPropBufferProvider.getPopertyText(componentType,isDetailInputComment,nodeId);
		if(commentValue!=null){
	        IDOMNode coment = (IDOMNode) domDocument.createComment(commentValue);
	        return coment;
		}
		return null;
	}
	
	
	/*
	 *	@author Fifteenth
	 *		明细表注释
	 */
	public static IDOMNode getDetailInputComentNode(String componentType,
			IDOMDocument domDocument,ObjColumnTo objColumnTo,String bizObjName,String nodeId){
		String commentJson = XmlPropBufferProvider.getPropertyJson(componentType);
		try {
			//绑定
			JSONObject json = new JSONObject(commentJson);
			json.put("alias", objColumnTo.getId());
			json.put("bizObj", bizObjName);
			json.put("field", objColumnTo.getId());
			json.put("datatype", objColumnTo.getDataType());

			String isPrimarry = objColumnTo.getIsprimary();
			if(objColumnTo.getIsprimary().equals("1")
					||objColumnTo.getIsprimary().equals("true")){
				json.put("isPK", true);
			}else{
				json.put("isPK", false);
			}
			
			json.put("needSubmit", true);
			
			boolean isDetailInputComment = true;
			String commentValue = XmlPropBufferProvider.getPopertyText(
					json.toString(),isDetailInputComment,nodeId);
			IDOMNode coment = (IDOMNode) domDocument.createComment(commentValue);
			return coment;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String getGlobleMapKey(String componentType){
		String key = componentType.toLowerCase();
		if(key.equals("input.text")){ //$NON-NLS-1$
			key = "input|text"; //$NON-NLS-1$
		}
		return key;
	}
	
	
	public static void setRef(Node headNode,IDOMDocument domDocument,
			String componentType,String jsORcs){
		
		String nodeName;
		String tagAttrPath;
		if(jsORcs.equals(jsRef)){
			nodeName = nodeName_SCRIPT;
			tagAttrPath = tagAttr_SRC;
		}else{
			nodeName = nodeName_LINK;
			tagAttrPath = tagAttr_HREF;
		}
		
		// existJSRefList:js引用节点列表
		ArrayList <Node>existRefList = new ArrayList<Node>();
		// existJSRefTextList:js引用节点内容列表
		ArrayList <String> existRefTextList =  new ArrayList<String>();
		
		//读取已有引用
		NodeList childNodeList = headNode.getChildNodes();
		if(childNodeList!=null){
			for(int childNum=0;childNum<childNodeList.getLength();childNum++){
				if(childNodeList.item(childNum).getNodeName().
						equals(nodeName)){
					Node node = childNodeList.item(childNum);
					existRefList.add(node);
					existRefTextList.add(
							node.
							getAttributes().getNamedItem(
									tagAttrPath).
									getNodeValue());
				}
			}
		}
		
		// 组件属性列表，用于得到引用
		String key = AbstractTagCreatorProvider.getGlobleMapKey(componentType);
		List <HashMap<String,Object>>propertylist = XmlPropBufferProvider.
				globleXmlMap.get(key);
		
		if(propertylist!=null){
			/*
			 *	@author Fifteenth
			 *		得到组件的引用
			 *
			 *		propertylist.get(index):index=0
			 */
			Object object = propertylist.get(0).get(jsORcs); 
			if(object instanceof ArrayList){
				
				// jsRefList组件的引用列表
				ArrayList refList = (ArrayList)object;
				int jsLength = refList.size();
				for(int refNum=0;refNum<refList.size();refNum++){
					/*
					 *	@author Fifteenth
					 *		1.组件已用过，refnum++
					 *		2.组件未引用过，生成
					 */
					if(existRefTextList.contains("../../../"+refList.get(refNum))){
						for(int compareNum=0;compareNum<existRefTextList.size();compareNum++){
							if(existRefTextList.get(compareNum).equals("../../../"+refList.get(refNum))){
								Node modifyRefNode = existRefList.get(compareNum);
								int attrRefNum = Integer.valueOf(modifyRefNode.getAttributes().getNamedItem("refNum").getNodeValue()); //$NON-NLS-1$
								attrRefNum++;
								modifyRefNode.getAttributes().
								getNamedItem(AbstractTagCreatorProvider.tagAttr_REFNUM).
								setNodeValue(attrRefNum+""); //$NON-NLS-1$ 
							}
						}
					}else{
						Element element;
						if(jsORcs.equals(jsRef)){
							element = domDocument.
									createElement(nodeName);
							
							// type
							element.setAttribute(AbstractTagCreatorProvider.tagAttr_TYPE, 
		        					"text/javascript");
							
							// src
							element.setAttribute(AbstractTagCreatorProvider.tagAttr_SRC, 
		        					"../../../"+refList.get(refNum).toString());
							
						}else{
							element = domDocument.
									createElement(nodeName);
							
							// rel
							element.setAttribute(AbstractTagCreatorProvider.tagAttr_REL, 
		        					"stylesheet");
							
							// type
							element.setAttribute(AbstractTagCreatorProvider.tagAttr_TYPE, 
		        					"text/css");
							
							// href
							element.setAttribute(AbstractTagCreatorProvider.tagAttr_HREF, 
		        					"../../../"+refList.get(refNum).toString());
						}
						
						
						
						// refNum
						element.setAttribute(
	        					AbstractTagCreatorProvider.tagAttr_REFNUM, "1"); //$NON-NLS-1$ 
	        			headNode.appendChild(element);
	        			// 加入引用后需要自己设置换行
	        			if(refNum==1||refNum < jsLength){
	        				headNode.appendChild(domDocument.createTextNode("\r")); //$NON-NLS-1$
	        			}
					}
				}
			}
		}
		
	}
}
