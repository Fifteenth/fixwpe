package org.eclipse.jst.pagedesigner.properties;


import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.json.JSONException;
import org.json.JSONObject;

public class FixAttributePropertySource implements IPropertySource {

	/*
	 * founderfix
	 * 代码说明
	 * 
	 * _provider属性提供对象
	 */
	FixAttributePropertySourceProvider _provider;

	
	FixAttributePropertySource(FixAttributePropertySourceProvider provider){
		_provider = provider;
	}
	
	public Object getEditableValue() {
		return null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		
		
		ArrayList list = new ArrayList();
		int length;
		
		int tagLength = 0 ;
		int modelLength = 0;
							
		Iterator tagJsonKeys=_provider.get_propertyJsonTag().keys();
		//tag
		while(tagJsonKeys.hasNext()){
			tagLength++;
			String key= tagJsonKeys.next().toString();
			list.add(key);
		}
		
		length = tagLength;
		//
		if(_provider.get_propertyJsonModel()!=null){
			Iterator modelJsonKeys=_provider.get_propertyJsonModel().keys();
			//model
			while(modelJsonKeys.hasNext()){
				modelLength++;
				String key= modelJsonKeys.next().toString();
				list.add(key);
			}
			
			length += modelLength;
			
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
			if(i<tagLength){
				descriptors[i].setCategory("Tag"); //$NON-NLS-1$
			}else{
				descriptors[i].setCategory("Model"); //$NON-NLS-1$
			}
		}
		return descriptors;
	}

	public Object getPropertyValue(Object id) {
		
		JSONObject _propertyJsonTag = _provider.get_propertyJsonTag();
		JSONObject _propertyJsonModel = _provider.get_propertyJsonModel();
		
		if(_propertyJsonTag.has(id.toString())){
			try {
				return _propertyJsonTag.get(id.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(_propertyJsonModel.has(id.toString())){
			try {
				//
				final Object valueObject = _propertyJsonModel.get(id.toString());
				if(valueObject instanceof JSONObject){
					/*
					 * 子属性页
					 */
					FixSubAttributePropertySource fixSubPropertySourc = 
							new FixSubAttributePropertySource((JSONObject)valueObject);
					return fixSubPropertySourc;
				}else{
					return _propertyJsonModel.get(id.toString());
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
		
	}

}
