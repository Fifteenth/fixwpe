/**
 * Copyright c FOUNDER CORPORATION 2011 All Rights Reserved.
 * ArrayTableHelper.java
 */
package com.founder.fix.ocx.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;


import com.founder.fix.base.wpe.FixImageProvider;
import com.founder.fix.fixwpe.formdesigner.ui.properties.editor.ArrayColumnTo;
import com.founder.fix.ocx.platformdesigner.interfaces.FixLoger;


/**
 * [类名]<br>
 * ArrayTableHelper.java<br>
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

public class ArrayTableHelper {
	
	private static int[] bounds = { 400 };

	private static TableViewer tableViewer;
	
	public static List<ArrayColumnTo> columnList;
	
	private static List<String> partitionJsonList = new ArrayList<String>();
	
	/**
	 * 构造器
	 */
	public ArrayTableHelper() {
		// TODO Auto-generated constructor stub
	}

	public static TableViewer createTable(final IManagedForm managedForm,
			Composite parent, String defaultValue,
			final List<ArrayColumnTo> columnList, HashMap<String, Object> map) {
		tableViewer = new TableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		
		createAttrColumns(parent, tableViewer);
		
		final Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		GridData gd = new GridData(SWT.LEFT);
		gd.heightHint = 250;
		gd.minimumHeight = 50;
		gd.widthHint = 385;
		
		table.setLayoutData(gd);
		
		table.addListener(SWT.MeasureItem, new Listener() { // TODO 修改行高度
					public void handleEvent(Event event) {
						event.width = table.getGridLineWidth();
						// 设置宽度
						event.height = (int) Math.floor(event.gc
								.getFontMetrics().getHeight() * 1.5);
					}
				});

		tableViewer.setContentProvider(new ArrayContentProvider());
		
		ArrayTableHelper.columnList = operationDeaultValue(defaultValue);
		
		tableViewer.setInput(ArrayTableHelper.columnList);
		
		
		//要打开属性
		map.put("arrayobject_dialog_state", "PropertySheetDialog");
		map.put("isArrayDialog", "true");
		
		CellEditor[] cellEditors = new CellEditor[tableViewer.getTable().getColumnCount()];
//		cellEditors[0] = new CustomDialogCellEditor(tableViewer.getTable(), map);
		
		tableViewer.setCellEditors(cellEditors);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setColumnProperties(BizObjContants.ARRAY_VALUE_IDS);
		tableViewer.setCellModifier(new ICellModifier() {

			public boolean canModify(Object element, String property) {
				return element instanceof ArrayColumnTo;
			}

			public Object getValue(Object element, String property) {
				ArrayColumnTo column = (ArrayColumnTo) element;
				if (property.equals(BizObjContants.ARRAY_VALUE_KEY))
					return column.getArrayValue();

				return null;
			}

			public void modify(Object element, String property, Object value) {
				ArrayColumnTo column = (ArrayColumnTo) ((Item) element).getData();
				Object oldValue = getValue(column, property);
				
				if (value.equals(oldValue))
					return;

				if (property.equals(BizObjContants.ARRAY_VALUE_KEY)) {
					column.setArrayValue((String) value);
				}
				
				tableViewer.refresh(column);
			}

		});

		return tableViewer;
	}
	
	public static ToolBar createToolBar(final IManagedForm managedForm,
			Composite parent, final TableViewer viewer,
			final List<ArrayColumnTo> columnList) {
		
		final ToolBar tbar = new ToolBar(parent, SWT.FLAT | SWT.HORIZONTAL);
		ToolItem titem = new ToolItem(tbar, SWT.NULL);

		titem.setImage(PlatformUI.getWorkbench().getSharedImages()
				.getImage(ISharedImages.IMG_OBJ_ADD));
		
		titem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				int size = columnList.size();
				String arrayValue = "{" + size + "}";

				ArrayColumnTo rct = new ArrayColumnTo(arrayValue);
				columnList.add(rct);
				
//				viewer.setInput(columnList);
				
				tbar.setFocus();
				
				refreshContent(rct);
			}
		});

		titem = new ToolItem(tbar, SWT.SEPARATOR);
		titem = new ToolItem(tbar, SWT.PUSH);
		titem.setImage(PlatformUI.getWorkbench().getSharedImages()
				.getImage(ISharedImages.IMG_TOOL_DELETE));
		final Shell shell = parent.getShell();

		titem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				ISelection sel = viewer.getSelection();
				if (sel == null)
					return;
				Object[] objs = ((IStructuredSelection) sel).toArray();
				if (objs == null || objs.length == 0)
					return;
				boolean b = MessageDialog.openConfirm(shell, "警告", "你确认要删除吗？");
				if (!b)
					return;
				
				int selected = viewer.getTable().getSelectionIndex();

				for (int i = 0; i < objs.length; i++) {
					ArrayColumnTo column = (ArrayColumnTo) objs[i];
					columnList.remove(column);
				}
				
				int num = 0; //删除后选中第几行数据的行数
				if(selected == 0) {
					num = selected;
				} else {
					num = selected - 1;
				}
				
				refreshContent(columnList.size() > 0 ? columnList.get(num) : null);
			}
		});
		
		titem = new ToolItem(tbar, SWT.SEPARATOR);
		titem = new ToolItem(tbar, SWT.PUSH);// 上移
		titem.setToolTipText("向上移动");
		titem.setImage(FixUtil.getImageFromURL(FixImageProvider.ARROW_TOP));

		titem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int selected = viewer.getTable().getSelectionIndex();
				if (selected <= 0)
					return;

				exchangeRow(columnList, selected, selected - 1);

				refreshContent(columnList.get(selected - 1));
			}
		});
		
		titem = new ToolItem(tbar, SWT.PUSH);// 下移
		titem.setToolTipText("向下移动");
		titem.setImage(FixUtil.getImageFromURL(FixImageProvider.ARROW_BOTTON));

		titem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int selected = viewer.getTable().getSelectionIndex();
				if (selected < 0
						|| selected == viewer.getTable().getItemCount() - 1)
					return;

				exchangeRow(columnList, selected, selected + 1);

				refreshContent(columnList.get(selected + 1));
			}
		});

		return tbar;
	}
	
	/**
	 * 交换两行内容
	 * 
	 * @param indexOne
	 * @param indexTwo
	 */
	private static void exchangeRow(List<ArrayColumnTo> list, 
			int indexOne, int indexTwo) {

		ArrayColumnTo oneCol = list.get(indexOne);
		ArrayColumnTo oneCopy = new ArrayColumnTo(oneCol.getArrayValue());

		list.set(indexOne, list.get(indexTwo));
		list.set(indexTwo, oneCol);
		tableViewer.refresh(list.get(indexOne));
		tableViewer.refresh(list.get(indexTwo));
	}
	
	private static void createAttrColumns(final Composite parent,
			final TableViewer viewer) {
		//列信息
		TableViewerColumn col = createTableViewerColumn(viewer,	"列信息", bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				ArrayColumnTo p = (ArrayColumnTo) element;
				return p.getArrayValue();
			}
		});
		
	}

	private static TableViewerColumn createTableViewerColumn(
			TableViewer viewer, String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}
	
	/**
	 * 刷新或者选择
	 * 
	 * @param selection
	 */
	private static void refreshContent(Object selection) {
		tableViewer.refresh();
		if (selection != null) {
			tableViewer.setSelection(new StructuredSelection(
					new Object[] { selection }), true);
		}
	}
	
	/**
	 * 将传递的值转为列表对象
	 * @param deaultValue
	 * @return
	 */
	private static List<ArrayColumnTo> operationDeaultValue(String deaultValue) {
		List<ArrayColumnTo> list = new ArrayList<ArrayColumnTo>();
		
		try {
			if(deaultValue != null && !deaultValue.equals("")) {
				deaultValue = deaultValue.replaceAll("\r\n", "").replaceAll(" ", "");
				
				deaultValue = deaultValue.substring(deaultValue.indexOf("[") + 1, 
						deaultValue.indexOf("]"));
				
				//重新初始化
				partitionJsonList = new ArrayList<String>();
				//分拆json
				partitionJson(deaultValue);
				
				for (int i = 0; i < partitionJsonList.toArray().length; i++) {
					String value = (String) partitionJsonList.toArray()[i];
					ArrayColumnTo arrayColumnTo = new ArrayColumnTo(value);
					list.add(arrayColumnTo);
				}
			}
		} catch (Exception e) {
			FixLoger.error("json解析错误，请检查值" + e.toString());
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 分拆json
	 * @param value
	 */
	private static void partitionJson(String value) {
		if(value != null && value.startsWith("{")) {
			String substring = value.substring(value.indexOf("{"), value.indexOf("}") + 1);
			partitionJsonList.add(substring);
			partitionJson(value.substring(value.indexOf("},") + 2));
		}
	}
}
