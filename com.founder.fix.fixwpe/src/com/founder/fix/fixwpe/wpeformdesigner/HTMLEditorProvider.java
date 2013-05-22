package com.founder.fix.fixwpe.wpeformdesigner;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import com.founder.fix.apputil.to.bizobj.BizObjTo;
import com.founder.fix.base.wpe.ConstantVariable;
import com.founder.fix.base.wpe.FormPageUtil;
import com.founder.fix.ocx.util.Utility;

public class HTMLEditorProvider {
	
public static void writeJS(String formPagePath,String jsText){
		
		BizObjTo bizObjTo = Utility.getBizObjToFromHtmlPath(formPagePath);
		String returnValue = "";
		
		String keyWord = ConstantVariable.fixAttributeNameSpace+"_";
        int keyLength = keyWord.length();
        String endTag = "-->";
        
        while(jsText.contains(keyWord)
        		&&jsText.contains(endTag)){
        	
        	
        	String fixjs = jsText.substring(
        			jsText.indexOf(keyWord),jsText.indexOf(endTag)); 
        	
        	
        	fixjs = fixjs.substring(fixjs.indexOf(keyWord)+keyLength, fixjs.length());
        	
        	String functionName = fixjs.substring(0,fixjs.indexOf(":"));
        	
        	fixjs = fixjs.substring(functionName.length()+":".length(),fixjs.length());
        	
        	try {
				JSONObject json = new JSONObject(fixjs);
				String jsString = "var "+functionName+"_Config = ";
				jsString = jsString + json.toString()+";";
				jsString = jsString + "\r";
				Object bizObjName;
				if(json.has("bizObj")){
					// key:bizObj
					bizObjName = json.get("bizObj");
					/*
					 * Fifteenth
					 * 		1.主表组件（字段的业务对象名=主业务对象名）
					 * 		2.明细表组件（字段的业务对象名!=主业务对象名）
					 */
					if(bizObjName!=null&&
							bizObjName.toString().equals(bizObjTo.getId())){
						jsString = jsString + "Fix.Engine.Map.add(\""+functionName+"\",\"fixbody\");";
					}else{
						jsString = jsString + "Fix.Engine.Map.add(\""+functionName+"\","+"\""+"detailtable_"+bizObjTo.getId()+"\");";
					}
				}
				
				returnValue += jsString + "\r";
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	jsText = jsText.substring(
        			jsText.indexOf(endTag)+endTag.length(),jsText.length());
        }
        
        BufferedOutputStream Buff=null;   
        FileOutputStream outSTr = null;  
        
        try {
        	String currentFormPagePath = FormPageUtil.currentFormPagePath;
        	String modelJSPath = (currentFormPagePath.substring(
        			0, currentFormPagePath.lastIndexOf("."))+"_model.js");
        	
        	outSTr = new FileOutputStream(
        			new File(modelJSPath));
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
