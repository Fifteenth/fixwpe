package com.founder.fix.fixwpe.formdesigner.ui.properties.editor;


import java.util.HashMap;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.json.JSONObject;

public class WPECustomDialogPropertyDescriptor extends PropertyDescriptor {
	
	private HashMap<String, Object> __node__map = null;
	
	/*
	 *	@author Fifteenth
	 *		_json可能是组件的json，也有可能是组件的某个属性的json
	 */
	private JSONObject _nodeJson = null;
	
//	private HashMap<String, Object> _translateMap;
	
	//OCX版
//	public WPECustomDialogPropertyDescriptor() {
//		super("aa","founderfix_property"); //$NON-NLS-1$ //$NON-NLS-2$
//	}
//
//	public WPECustomDialogPropertyDescriptor(HashMap<String, Object> map) {
//		super(map.get("name").toString(), map.get("caption").toString()); //$NON-NLS-1$ //$NON-NLS-2$
//		this.Map = map;
//	}
//	
//	public WPECustomDialogPropertyDescriptor(HashMap<String, Object> map, Text text) {
//		super(map.get("name").toString(), map.get("caption").toString()); //$NON-NLS-1$ //$NON-NLS-2$
//		this.Map = map;
//	}
	
	
	
	public WPECustomDialogPropertyDescriptor(
			HashMap<String, Object> node_map,JSONObject node_json,
			Text dialogText ) {
		super(node_map.get("name").toString(), node_map.get("caption").toString()); //$NON-NLS-1$ //$NON-NLS-2$
		this.__node__map = node_map;
		this._nodeJson = node_json;
	}
	
	public WPECustomDialogPropertyDescriptor(
			HashMap<String, Object> node_map,JSONObject node_json) {
		super(node_map.get("name").toString(), node_map.get("caption").toString()); //$NON-NLS-1$ //$NON-NLS-2$
		__node__map = node_map;
		_nodeJson = node_json;
	}

	//WPE版
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new WPECustomDialogCellEditor(
				parent, __node__map,_nodeJson
//				,_translateMap
				);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}
}