package com.founder.fix.jst.pagedesigner.itemcreation;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;


import com.founder.fix.studio.formdesigner.ui.properties.xml.XmlPropBuffer;

/**
 * @author Fifteenth
 */
public class AbstractTagCreatorProvider {

	public static XmlPropBuffer xmlProp;
	public static HashMap<String, ArrayList<HashMap<String, Object>>> globleXmlMap;
	
	public static ArrayList<HashMap<String,Object>> componentList;
	
	public static boolean initPropertyFlag = false;
	    /**
	     * 
	     */
	 
	public static void initProperty(String htmlFileName){
		if(globleXmlMap==null){
			if(xmlProp==null){
				xmlProp = new XmlPropBuffer(htmlFileName);
			}
			globleXmlMap = xmlProp.getStudioConfigXml();
		}
	}
	
	
	
	
	
	public static String getPopertyMap(ArrayList tagMapList,
			String tagName,String commentValue){
		//i=0的时候特殊处理
		if(tagMapList==null){
			return "";
		}
		
		int length = tagMapList.size();
    	for(int i=0+1;i<tagMapList.size();i++){
    		/*
    		 * 包含关键字element
    		 */
    		HashMap mapVar= (HashMap)tagMapList.get(i);
    		if(mapVar.containsKey("element")){
    			String keyVar = (mapVar).get("element").toString().toLowerCase(); //$NON-NLS-1$
    			ArrayList tempList = globleXmlMap.get(keyVar);
    			
    			if(tempList==null){
    				return commentValue.substring(0, commentValue.length()-",".length());
    			}
    			
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
	
	
	public static HashMap<String, Object> getXMLProperty(String tagName,String attibuteName){
		
		return xmlProp.getProperty(tagName, attibuteName);
	}
	
	public static String getPopertyJson(String tagName){
		
		tagName = tagName.toLowerCase();
		
		String commentValue = "@FixAttributies:{"; //$NON-NLS-1$
        
        ArrayList tagMaplist;
        if(globleXmlMap.containsKey(tagName)){
        	
        	tagMaplist = globleXmlMap.get(tagName); //$NON-NLS-1$
        	
        	commentValue = getPopertyMap(tagMaplist,tagName,commentValue);
        	
        	return commentValue+"}";
        }
		return "";
	}
	
	
	
	public static void getComponentJson(){
		
		if(componentList==null){
			if(xmlProp==null){
				//ywpx
				xmlProp = new XmlPropBuffer("D:/mySelfWorkSpaces/runtime-exe/test1/WebRoot/NewFile1.jsp");
			}
			componentList = xmlProp.getComponentList();;
		}
	}

	
	public static void writeJS(String jsText){
		
		String returnValue = "";
		
		String keyWord = "FixAttributies:";//$NON-NLS-1$
        int keyLength = keyWord.length();
        
        
        String endTag = "-->";//$NON-NLS-1$
        
        while(jsText.contains(keyWord)){
        	
        	
        	String fixjs = jsText.substring(
        			jsText.indexOf(keyWord),jsText.indexOf(endTag)); 
        	
        	
        	fixjs = fixjs.substring(fixjs.indexOf(keyWord)+keyLength, fixjs.length());
        	
        	try {
				JSONObject json = new JSONObject(fixjs);
				String id = (String) json.get("id");
				
				String s1 = "<SCRIPT scope=\"component\" eventType=\"config\" forid=\"";
				s1 += id + "\"> var "+id+"_Config = ";
				fixjs = s1 + fixjs+";</SCRIPT>";
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//        	System.out.println(fixjs);
        	

        	returnValue += fixjs + "\r";
        	
        	jsText = jsText.substring(
        			jsText.indexOf(endTag)+endTag.length(),jsText.length());
        }
        
        BufferedOutputStream Buff=null;   
        FileOutputStream outSTr = null;  
        
        try {
        	outSTr = new FileOutputStream(
        			new File("D:/mySelfWorkSpaces/runtime-exe/test1/WebRoot/add11.js"));
			Buff=new BufferedOutputStream(outSTr);   
	     
			Buff.write(returnValue.getBytes());   
			Buff.flush();   
			Buff.close(); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	}
}
