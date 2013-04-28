package com.founder.fix.fixwpe.formdesigner.ui.properties.editor;


import java.util.HashMap;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.json.JSONObject;

public class WPECustomDialogPropertyDescriptor extends PropertyDescriptor {
	
	private HashMap<String, Object> _tag_attr_map = null;
	
	/*
	 *	@author Fifteenth
	 *		_json可能是组件的json，也有可能是组件的某个属性的json
	 */
	private JSONObject _tagOrAttrJson = null;
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
			HashMap<String, Object> tag_attr_map,JSONObject _json,
//			HashMap<String, Object> translateMap,
			Text dialogText ) {
		super(tag_attr_map.get("name").toString(), tag_attr_map.get("caption").toString()); //$NON-NLS-1$ //$NON-NLS-2$
		this._tag_attr_map = tag_attr_map;
		this._tagOrAttrJson = _json;
//		_translateMap = translateMap;
	}
	
	public WPECustomDialogPropertyDescriptor(
			HashMap<String, Object> tag_attr_map,JSONObject tag_json
//			,HashMap<String, Object> translateMap
			) {
		super(tag_attr_map.get("name").toString(), tag_attr_map.get("caption").toString()); //$NON-NLS-1$ //$NON-NLS-2$
		_tag_attr_map = tag_attr_map;
		_tagOrAttrJson = tag_json;
//		_translateMap = translateMap;
	}

//	//OCX版
//	public CellEditor createPropertyEditor(Composite parent) {
//		CellEditor editor = new WPECustomDialogCellEditor(parent, this.Map);
//		if (getValidator() != null)
//			editor.setValidator(getValidator());
//		return editor;
//	}

	//WPE版
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new WPECustomDialogCellEditor(
				parent, _tag_attr_map,_tagOrAttrJson
//				,_translateMap
				);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}
}