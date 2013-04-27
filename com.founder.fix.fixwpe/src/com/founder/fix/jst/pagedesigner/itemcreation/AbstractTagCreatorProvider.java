package com.founder.fix.jst.pagedesigner.itemcreation;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Element;

import com.founder.fix.studio.formdesigner.ui.properties.xml.XmlPropBuffer;

/**
 * @author Fifteenth
 */
public class AbstractTagCreatorProvider {

	public static XmlPropBuffer xmlProp;
	public static HashMap<String, ArrayList<HashMap<String, Object>>> globleXmlMap;
	
	public static boolean initPropertyFlag = false;
	    /**
	     * 
	     */
	 
	public static void initProperty(String htmlFileName){
		if(xmlProp==null){
			xmlProp = new XmlPropBuffer(htmlFileName);
			globleXmlMap = xmlProp.getStudioConfigXml();
		}
	}
	
	
	
	public static String getPopertyMap(ArrayList tagMapList,String tagName,String commentValue){
		//i=0的时候特殊处理
		int length = tagMapList.size();
    	for(int i=0+1;i<tagMapList.size();i++){
    		/*
    		 * 包含关键字element
    		 */
    		HashMap mapVar= (HashMap)tagMapList.get(i);
    		if(mapVar.containsKey("element")){
    			String keyVar = (mapVar).get("element").toString().toLowerCase(); //$NON-NLS-1$
    			ArrayList tempList = globleXmlMap.get(keyVar);
    			
    			if(i<length-1){
    				commentValue = getPopertyMap(tempList,keyVar,
        					commentValue+(mapVar).get("caption").toString()+":{" )+"},";
    			}else{
    				commentValue = getPopertyMap(tempList,keyVar,
        					commentValue+(mapVar).get("caption").toString()+":{" )+"}";
    			}
    			
    		}
    		else{
    			String property = (mapVar).get("caption").toString(); //$NON-NLS-1$
        		
        		commentValue += property+":\"\""; //$NON-NLS-1$
        		if(i<length-1){
        			commentValue += ","; //$NON-NLS-1$
        		}
        		
        		//每行输出5个属性
//        		if(i%5==0){
//        			commentValue += "\r"; //$NON-NLS-1$
//        		}
//        		if(i==length-1){
//        			commentValue +="}";
//        		}
    		}
    	}
		return commentValue;
	}
	
	public static String getPopertyJson(Element ele){
		
		String commentValue = "@FixAttributies:{"; //$NON-NLS-1$
        
        String tagName = ele.getAttribute("ComponentType").toLowerCase(); //$NON-NLS-1$
        
        ArrayList tagMaplist;
        if(globleXmlMap.containsKey(tagName)){
        	
        	tagMaplist = globleXmlMap.get(ele.getAttribute("ComponentType").toLowerCase()); //$NON-NLS-1$
        	
        	commentValue = getPopertyMap(tagMaplist,tagName,commentValue);
        	
        	return commentValue+"}";
        }
		return "";
	}
}
