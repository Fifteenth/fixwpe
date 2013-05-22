/**
 * 
 */
package com.founder.fix.fixwpe.formdesigner.ui.properties.editor;

import java.util.HashMap;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * @author wangzhiwei
 *
 */
public class FixTextPropertyDescriptor extends TextPropertyDescriptor {
	
	private HashMap<String, Object> Map = null;
	private Text text;

	/**
	 * @param id
	 * @param displayName
	 */
	public FixTextPropertyDescriptor(Object id, String displayName,
			HashMap<String, Object> map, Text text) {
		super(id, displayName);
		this.Map = map;
		this.text = text;
	}
	
	
	/**
	 * @param id
	 * @param displayName
	 */
	public FixTextPropertyDescriptor(Object id, String displayName,
			HashMap<String, Object> map) {
		super(id, displayName);
		this.Map = map;
	}
	

	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new FixTextCellEditor(parent, 
				SWT.NONE, this.Map, text);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}
}
