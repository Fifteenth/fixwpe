package com.founder.fix.jst.pagedesigner.properties;


import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.json.JSONException;
import org.json.JSONObject;

public class FixSubAttributePropertySource implements IPropertySource {

	/*
	 * founderfix
	 * 代码说明
	 * 
	 * _provider属性提供对象
	 */
	private JSONObject _jsonObject;
	private JSONObject _childJsonObject;
	
	private int _propertyJsonNum;
	
	private FixPropertySource _fixPropertySource;
	
	private Object saveValue;
	private Object saveId;
	private boolean childSaveMark=false;

	FixSubAttributePropertySource(FixPropertySource fixPropertySource,int propertyJsonNum){
		_fixPropertySource = fixPropertySource;
		_propertyJsonNum = propertyJsonNum;
		
		_jsonObject = fixPropertySource.getChildPropertyJsons()[_propertyJsonNum];
	}
	
	
	FixSubAttributePropertySource(JSONObject jsonObject){
		_jsonObject = jsonObject;
	}
	
	public Object getEditableValue() {
		return null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		int length=0;
		ArrayList list = new ArrayList();
		
		Iterator childJsonKeys=_jsonObject.keys();
		
		while(childJsonKeys.hasNext()){
			length++;
			String key= childJsonKeys.next().toString();
			list.add(key);
		}
		
		PropertyDescriptor[] descriptors = new PropertyDescriptor[length];
		for(int i=0;i<length;i++){
			/*
			 * founderfix
			 * 注释
			 * 
			 * Parameters：1.用于显示value，用于显示属性名称;
			 * 
			 */
			descriptors[i] = new TextPropertyDescriptor(list.get(i), list.get(i).toString());
		}
		return descriptors;
	}

	public Object getPropertyValue(Object id) {
		
		if(_jsonObject.has(id.toString())){
			Object valueObject;
			try {
				valueObject = _jsonObject.get(id.toString());
				if(valueObject instanceof JSONObject){
					_childJsonObject = (JSONObject)valueObject;
					FixSubAttributePropertySource fixSubPropertySource = 
							new FixSubAttributePropertySource(_childJsonObject);
					return fixSubPropertySource;
				}else{
					return _jsonObject.get(id.toString());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ""; //$NON-NLS-1$
	}

	public boolean isPropertySet(Object id) {
		return false;
	}

	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stub
		
	}

	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub
		if(id!=null&&value==null
				&&childSaveMark
				){
			
		}
		else if(!id.equals(saveId)||!value.equals(saveValue)){
			try {
				_jsonObject.put(id.toString(), value.toString());
				_fixPropertySource.setChildSaveMark(true);
				
				JSONObject[] jsons = new JSONObject[2];
				jsons[_propertyJsonNum] = _jsonObject;
				_fixPropertySource.setChildPropertyJsons(jsons);
				
				saveId = id;
				saveValue = value;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
