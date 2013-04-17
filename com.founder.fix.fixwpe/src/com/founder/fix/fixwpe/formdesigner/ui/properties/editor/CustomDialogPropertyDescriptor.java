package com.founder.fix.fixwpe.formdesigner.ui.properties.editor;


import java.util.HashMap;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class CustomDialogPropertyDescriptor extends PropertyDescriptor {
	
	private HashMap<String, Object> Map = null;
	
	
	/**
	 * 
	 */
	public CustomDialogPropertyDescriptor() {
		super("aa","founderfix_property"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public CustomDialogPropertyDescriptor(HashMap<String, Object> map) {
		super(map.get("name").toString(), map.get("caption").toString()); //$NON-NLS-1$ //$NON-NLS-2$
		this.Map = map;
	}
	
	public CustomDialogPropertyDescriptor(HashMap<String, Object> map, Text text) {
		super(map.get("name").toString(), map.get("caption").toString()); //$NON-NLS-1$ //$NON-NLS-2$
		this.Map = map;
	}

	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new CustomDialogCellEditor(parent, this.Map);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}

}