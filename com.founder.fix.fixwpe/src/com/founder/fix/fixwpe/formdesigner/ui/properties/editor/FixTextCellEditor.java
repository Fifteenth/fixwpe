/**
 * 
 */
package com.founder.fix.fixwpe.formdesigner.ui.properties.editor;

import java.util.HashMap;

import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * @author wangzhiwei
 *
 */
public class FixTextCellEditor extends TextCellEditor {
	
	private Text displayText;

	private HashMap<String, Object> Map = null;

	/**
	 * 
	 */
	public FixTextCellEditor() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param parent
	 */
	public FixTextCellEditor(Composite parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param parent
	 * @param style
	 */
	public FixTextCellEditor(Composite parent, int style,
			HashMap<String, Object> map, Text text) {
		super(parent, style);
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
