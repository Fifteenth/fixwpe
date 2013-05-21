package com.founder.fix.fixwpe.wpeformdesigner.ui.properties.editor;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.json.JSONObject;

public class WPECustomDialogPropertyDescriptor extends PropertyDescriptor {
	
	private HashMap<String, Object> _propertyMap = null;
	private JSONObject _modelJson = null;
	private HashMap<String, String> _translateMap;
	private HashMap<String,ArrayList<HashMap<String,Object>>> _parentMap;
	private String _parentNodeName;
	
	public WPECustomDialogPropertyDescriptor(
			HashMap<String, Object> propertyMap,JSONObject modelJson,
			HashMap<String, String> translateMap,
			Text dialogText,
			HashMap<String,ArrayList<HashMap<String,Object>>> parentMap,String parentNodeName) {
		super(propertyMap.get("name").toString(), propertyMap.get("caption").toString()); //$NON-NLS-1$ //$NON-NLS-2$
		_propertyMap = propertyMap;
		_modelJson = modelJson;
		_translateMap = translateMap;
		_parentMap = parentMap;
		_parentNodeName = parentNodeName;
	}
	
	public WPECustomDialogPropertyDescriptor(
			HashMap<String, Object> node_map,JSONObject node_json,
			HashMap<String, String> translateMap,
			HashMap<String,ArrayList<HashMap<String,Object>>> parentMap,String parentNodeName) {
		super(node_map.get("name").toString(), node_map.get("caption").toString()); //$NON-NLS-1$ //$NON-NLS-2$
		_propertyMap = node_map;
		_modelJson = node_json;
		_translateMap = translateMap;
		_parentMap = parentMap;
		_parentNodeName = parentNodeName;
	}

	//WPE版
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new WPECustomDialogCellEditor(
				parent, _propertyMap,_modelJson
				,_translateMap,
				_parentMap,_parentNodeName
				);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}
}