/**
 * 
 */
package com.founder.fix.fixwpe.formdesigner.ui.properties.editor;

import java.util.HashMap;

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * @author wangzhiwei
 *
 */
public class FixComboBoxCellEditor extends ComboBoxCellEditor {
	
	private Text displayText;

	private HashMap<String, Object> Map = null;

	/**
	 * 
	 */
	public FixComboBoxCellEditor() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param parent
	 * @param items
	 */
	public FixComboBoxCellEditor(Composite parent, String[] items) {
		super(parent, items);
	}

	/**
	 * @param parent
	 * @param items
	 * @param style
	 */
	public FixComboBoxCellEditor(Composite parent, String[] items, int style,
			HashMap<String, Object> map, Text text) {
		super(parent, items, style);
		this.Map = map;
		this.displayText = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.DialogCellEditor#doSetFocus()
	 */
	@Override
	protected void doSetFocus() {
		//获取焦点用以提示信息
		String description = String.valueOf(Map.get("description"));
		if(displayText != null) {
			displayText.setText(description == null ? "" : description);
		}
	}
	
}
