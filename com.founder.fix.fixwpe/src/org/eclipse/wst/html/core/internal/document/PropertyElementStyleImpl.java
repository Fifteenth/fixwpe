package org.eclipse.wst.html.core.internal.document;

import org.eclipse.jst.pagedesigner.properties.FixPropertySource;
import org.json.JSONObject;

/**
 * @author Fifteenth
 */
@SuppressWarnings("restriction")
public class PropertyElementStyleImpl extends ElementStyleImpl {
	
	
	
	
	
	private FixPropertySource propertySource;
	private JSONObject objectJson;

	
	public PropertyElementStyleImpl(){
		super();
	}
	
	public PropertyElementStyleImpl(ElementStyleImpl that){
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
