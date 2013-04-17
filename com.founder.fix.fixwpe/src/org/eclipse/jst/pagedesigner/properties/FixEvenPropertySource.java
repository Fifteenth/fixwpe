package org.eclipse.jst.pagedesigner.properties;


import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
//import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.json.JSONException;
import org.json.JSONObject;

import com.founder.fix.fixwpe.formdesigner.ui.properties.editor.CustomDialogPropertyDescriptor;

/**
 * @author Fifteenth
 */
public class FixEvenPropertySource implements IPropertySource {

	
	private FixEvenPropertySourceProvider _provider;
	private String tagHashCode;

	
	public FixEvenPropertySource(FixEvenPropertySourceProvider provider){
		_provider = provider;
	}
	
	

	public Object getEditableValue() {
		return null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		
		
		ArrayList list = new ArrayList();
		int length = 0;
		
		int tagLength = 0 ;
//		int modelLength = 0;
		
		
		if(_provider.get_evenJson()!=null){
			Iterator tagJsonKeys=_provider.get_evenJson().keys();
			//tag
			while(tagJsonKeys.hasNext()){
				tagLength++;
				String key= tagJsonKeys.next().toString();
				list.add(key);
			}
			
			length = tagLength;
		}
							
		
		
//		if(_provider.get_evenJson()!=null){
//			Iterator modelJsonKeys=_provider.get_evenJson().keys();
//			//model
//			while(modelJsonKeys.hasNext()){
//				modelLength++;
//				String key= modelJsonKeys.next().toString();
//				list.add(key);
//			}
//			
//			length += modelLength;
//			
//		}
		
		PropertyDescriptor[] descriptors = new PropertyDescriptor[length];
		
		for(int i=0;i<length;i++){
			/*
			 * founderfix
			 * 注释
			 * 
			 * Parameters：1.用于显示value，用于显示属性名称;
			 * 
			 */
			descriptors[i] = new CustomDialogPropertyDescriptor();
//			if(i<tagLength){
//				descriptors[i].setCategory("Tag"); //$NON-NLS-1$
//			}else{
				descriptors[i].setCategory("Model"); //$NON-NLS-1$
//			}
		}
		return descriptors;
	}

	public Object getPropertyValue(Object id) {
		
		JSONObject _evenJson = _provider.get_evenJson();
		
		if(_evenJson.has(id.toString())){
			try {
				return _evenJson.get(id.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(_evenJson.has(id.toString())){
			try {
				//
				final Object valueObject = _evenJson.get(id.toString());
				if(valueObject instanceof JSONObject){
					/*
					 * 子属性页
					 */
//					FixSubAttributePropertySource fixSubPropertySourc = 
//							new FixSubAttributePropertySource((JSONObject)valueObject);
//					return fixSubPropertySourc;
					System.out.println();
				}else{
					return _evenJson.get(id.toString());
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



	public String getTagHashCode() {
		return tagHashCode;
	}



	public void setTagHashCode(String tagHashCode) {
		this.tagHashCode = tagHashCode;
	}

}
