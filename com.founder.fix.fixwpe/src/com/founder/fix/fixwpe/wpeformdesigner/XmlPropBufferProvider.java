package com.founder.fix.fixwpe.wpeformdesigner;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.founder.fix.base.wpe.FormPageUtil;


public class XmlPropBufferProvider {

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
    			if(keyVar.equals("input.text")){        				 //$NON-NLS-1$
    				keyVar = "input|text"; 
    			}
    			ArrayList tempList = globleXmlMap.get(keyVar);
    			
    			if(tempList==null){
    				return commentValue.substring(0, commentValue.length()-",".length());
    			}
    			
    			if(i<length-1){
    				commentValue = getPopertyMap(tempList,keyVar,
        					commentValue+(mapVar).get("name").toString()+":{" )+"},";
    			}else{
    				commentValue = getPopertyMap(tempList,keyVar,
        					commentValue+(mapVar).get("name").toString()+":{" )+"}";
    			}
    			
    		}
    		else{
    			String property = (mapVar).get("name").toString(); //$NON-NLS-1$
    			// id不写到注释中
    			if(!property.equals("id")){
    				Object defaultString = (mapVar).get("default");
        			if(defaultString!=null){
        				if(defaultString.toString().contains("{")
        						&&defaultString.toString().contains("}")){
        					commentValue += property+":"+defaultString; //$NON-NLS-1$
        				}else{
        					commentValue += property+":\""+defaultString+"\""; //$NON-NLS-1$
        				}
        			}else{
        				commentValue += property+":\"\""; //$NON-NLS-1$
        			}
            		
            		if(i<length-1){
            			commentValue += ","; //$NON-NLS-1$
            		}
            		
            		//每行输出5个属性
//            		if(i%5==0){
//            			commentValue += "\r"; //$NON-NLS-1$
//            		}
//            		if(i==length-1){
//            			commentValue +="}";
//            		}
    			}
    		}
    	}
		return commentValue;
	}
	
	
	public static HashMap<String, Object> getXMLProperty(String tagName,String attibuteName){
		
		return xmlProp.getProperty(tagName, attibuteName);
	}
	
	public static String getPropertyJson(String tagName){
		
		tagName = tagName.toLowerCase();
		
		if(tagName.equals("input.text")){ 
			tagName = "input|text"; 
		}
		
		String commentValue = "{"; //$NON-NLS-1$
        
        ArrayList tagMaplist;
        if(globleXmlMap.containsKey(tagName)){
        	
        	tagMaplist = globleXmlMap.get(tagName); //$NON-NLS-1$
        	
        	commentValue = getPopertyMap(tagMaplist,tagName,commentValue);
        	
        	return commentValue+"}";
        }
		return "";
	}
	
	
	
	public static String getPopertyText(String string,boolean isJson,String nodeId){
		if(!isJson){
			string = getPropertyJson(string);
			if(string.equals("")){
				return "";
			}
		}
		String commentValue = "@FixAttributies"+"_"+nodeId+":"; //$NON-NLS-1$
        return commentValue+string;
	}
	
	
	
	public static void getComponentJson(){
		
		if(componentList==null){
			if(xmlProp==null){
				xmlProp = new XmlPropBuffer(FormPageUtil.currentFormPagePath);
			}
			componentList = xmlProp.getComponentList();;
		}
	}

	
}
