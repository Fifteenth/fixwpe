/**
 * 
 */
package com.founder.fix.fixwpe.formdesigner.ui.properties.editor;

import java.util.HashMap;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;

/**
 * @author wangzhiwei
 *
 */
public class FixComboBoxPropertyDescriptor extends ComboBoxPropertyDescriptor {
	
	private HashMap<String, Object> Map = null;
	private Text text;
	private String[] labelsArray;

	/**
	 * @param id
	 * @param displayName
	 * @param labelsArray
	 */
	public FixComboBoxPropertyDescriptor(Object id, String displayName,
			String[] labelsArray) {
		super(id, displayName, labelsArray);
		// TODO Auto-generated constructor stub
	}

	public FixComboBoxPropertyDescriptor(Object id, String displayName,
			String[] labelsArray, HashMap<String, Object> map, Text text) {
		super(id, displayName, labelsArray);
		this.labelsArray = labelsArray;
		this.Map = map;
		this.text = text;
	}
	
	public FixComboBoxPropertyDescriptor(Object id, String displayName,
			String[] labelsArray, HashMap<String, Object> map) {
		super(id, displayName, labelsArray);
		this.labelsArray = labelsArray;
		this.Map = map;
	}
	
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new FixComboBoxCellEditor(parent, labelsArray,
				SWT.READ_ONLY, this.Map, text);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}
}
