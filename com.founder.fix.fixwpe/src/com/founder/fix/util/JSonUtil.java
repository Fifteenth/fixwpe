package com.founder.fix.util;

import org.json.JSONException;
import org.json.JSONObject;

public class JSonUtil {

	
	/*
	 * 得到子JSON
	 */
	public static JSONObject getChildJosn(JSONObject objectJson,String childJsonType){
		
		try {
			if(objectJson!=null&&
					objectJson.has(childJsonType)){
				return new JSONObject(objectJson.get(childJsonType).toString());
			}else{
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
