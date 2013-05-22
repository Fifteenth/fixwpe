/**
 * Copyright c FOUNDER CORPORATION 2011 All Rights Reserved.
 * ArrayTable.java
 */
package com.founder.fix.fixwpe.formdesigner.ui.properties.editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

import com.founder.fix.base.wpe.FixImageProvider;
import com.founder.fix.ocx.util.ArrayTableHelper;
import com.founder.fix.ocx.util.FixUtil;
import com.founder.fix.ocx.util.WindowStyle;


/**
 * [类名]<br>
 * ArrayTable.java<br>
 * <br>
 * [功能概要]<br>
 * 
 * <br>
 * [变更履历]<br>
 * 
 * <br>
 * 2011-10-13 ver1.0 <br>
 * <br>
 * 
 * @作者 wangzhiwei
 * 
 */

public class ArrayDialog extends TitleAreaDialog implements
		ModifyListener {
	
	private String value;
	
	private String defaultValue;
	
	private TableViewer viewer;
	
	private List<ArrayColumnTo> arrays;
	
	private HashMap<String, Object> Map;

	public ArrayDialog(Shell parentShell, String defaultValue,
			HashMap<String, Object> map) {
		super(parentShell);
		setHelpAvailable(false);
		this.defaultValue = defaultValue;
		arrays = new ArrayList<ArrayColumnTo>();
		this.Map = map;
	}

	protected Point getInitialSize() {
		return new Point(550, 500);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		
		GridLayout layout = new GridLayout();
		layout.marginLeft = 50;
		layout.marginTop = 5;
		layout.marginRight = 50;

		WindowStyle.setCenter(getShell());
		
		container.setLayout(layout);
		createContentArea(container);

		setTitle("新增列信息配置");
		setTitleImage(FixUtil.getImageFromURL(FixImageProvider.CREATE_FILE_DIALOG));

		return container;
	}

	private void createContentArea(Composite container) {
		Composite parent = new Composite(container, SWT.NULL);
		parent.setLayout(new GridLayout());

		Composite firstClient = new Composite(parent, SWT.NULL);
		firstClient.setLayout(new GridLayout(3, false));

		Section section = new Section(parent, Section.TWISTIE
				| ExpandableComposite.EXPANDED);
		GridData secGd = new GridData(GridData.FILL_HORIZONTAL);
		secGd.horizontalSpan = 3;
		section.setLayoutData(secGd);
		section.setText("所有列信息：");

		Composite client = new Composite(section, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);
		client.setLayoutData(new GridData(400, 100));

		//创建表格
		viewer = ArrayTableHelper.createTable(null, client, defaultValue, arrays, this.Map);
		
		//创建工具条
		ToolBar tbar = ArrayTableHelper.createToolBar(null, section, viewer, ArrayTableHelper.columnList);

		section.setTextClient(tbar);
		section.setClient(client);
	}

	/**
	 * 设置对话框在中心位置
	 * 
	 */
	private void setDialogLocation() {
		Rectangle monitorArea = getShell().getDisplay().getPrimaryMonitor()
				.getBounds();
		Rectangle shellArea = getShell().getBounds();
		int x = monitorArea.x + (monitorArea.width - shellArea.width) / 2;
		int y = monitorArea.y + (monitorArea.height - shellArea.height) / 2;
		getShell().setLocation(x, y);
	}

	/**
	 * 修改button的名称
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		// create OK and Cancel buttons by default
		createButton(parent, IDialogConstants.OK_ID, "确定", true);
		createButton(parent, IDialogConstants.CANCEL_ID, "关闭", false);
//		getButton(IDialogConstants.OK_ID).setEnabled(false);
	}

	// 处理点击完成按钮
	@Override
	protected void okPressed() {
		//将所有的CellEditor失去焦点将其值保存
		CellEditor[] editors = viewer.getCellEditors();
//		for (int i = 0; i < editors.length; i++) {
//			CustomDialogCellEditor cellEditor = (CustomDialogCellEditor) editors[i];
//			cellEditor.focusLost();
//		}
		
		//收集数据，拼json
		TableItem[] items = viewer.getTable().getItems();
		
		//过滤后的数组
		List <String> list = new ArrayList<String>();
		for (int i = 0; i < items.length; i++) {
			TableItem item = items[i];
			String text = item.getText();
			if(text.contains("{"))
			{
				text = text.substring(1, text.length());
			}
			if(text.contains("}")){
				text = text.substring(0, text.length()-1);
			}
			//过滤掉为空的
			if(!text.matches("^[0-9]\\d*$")
					&&!text.equals("")){
				list.add(text);
			}
		}
		for(int i=0;i<list.size();i++)
		{
			if(i == 0) {
				if(i == list.size() - 1) {
					value = "[" + "{"+ list.get(i)+"}" + "]";
				} else {
					value = "[" + "{"+ list.get(i)+"}";
				}
			} else if(i != 0 && i == list.size() - 1) {
				value += "," + "{"+ list.get(i)+"}" + "]";
			} else {
				value += "," + "{"+ list.get(i)+"}";
			}
		}
		
		if(value == null) {
			value = new String();
		}
		super.okPressed();
	}

	public String getValue() {
		return value;
	}

	public void modifyText(ModifyEvent e) {
		getButton(IDialogConstants.OK_ID).setEnabled(canComplete());
	}

	private boolean canComplete() {
		return getErrorMessage() == null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
	 */
	@Override
	protected void buttonPressed(int buttonId) {
		if(buttonId == OK) {
			if(value != null) {
				
			} else {
//				setErrorMessage("不能为空");
//				return;
			}
		}
		super.buttonPressed(buttonId);
	}
}
