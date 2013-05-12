package com.founder.fix.wst.html.core.internal.document;

import org.eclipse.wst.html.core.internal.document.ElementStyleImpl;
import org.json.JSONObject;

import com.founder.fix.jst.pagedesigner.properties.FixPropertySource;

/**
 * @author Fifteenth
 */
@SuppressWarnings("restriction")
public class FixElementStyleImpl extends ElementStyleImpl {
	
	private FixPropertySource propertySource;
	private JSONObject objectJson;

	
	public FixElementStyleImpl(){
		super();
	}
	
	public FixElementStyleImpl(ElementStyleImpl that){
		super(that);
	}
	
	
	public FixPropertySource getPropertySource() {
		return propertySource;
	}

	public void setPropertySource(FixPropertySource propertySource) {
		this.propertySource = propertySource;
	}

	
	public JSONObject getObjectJson() {
		return objectJson;
	}

	public void setObjectJson(JSONObject objectJson) {
		this.objectJson = objectJson;
	}

	
}
