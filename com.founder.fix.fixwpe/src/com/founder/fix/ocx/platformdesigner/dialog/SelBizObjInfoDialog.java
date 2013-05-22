package com.founder.fix.ocx.platformdesigner.dialog;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;


import com.founder.fix.apputil.to.bizobj.BizObjTo;
import com.founder.fix.apputil.to.bizobj.ObjColumnTo;
import com.founder.fix.base.platformdesigner.Entity.project.BizobjEntity;
import com.founder.fix.base.wpe.FixImageProvider;
import com.founder.fix.ocx.platformdesigner.interfaces.FixLoger;
import com.founder.fix.ocx.util.BizObjContants;
import com.founder.fix.ocx.util.CacheUtil;
import com.founder.fix.ocx.util.FixUtil;
import com.founder.fix.ocx.util.FormConst;
import com.founder.fix.ocx.util.FormConst.SelfBizObjInfo;

public class SelBizObjInfoDialog extends TitleAreaDialog implements Listener {
	private static int[] bounds = { 160, 160, 100 };

	public static final String NAME = "MetaDataInfo";
	private Combo dataSourceCombo;
	private ComboViewer metaDataCombo;
	private TableViewer viewer;
	private CheckboxTableViewer checkTableViewer;
	private int selectedDataType;
//	private Resource selectedDBSource;
//	private List<Resource> dataSourceList;
//	private ComboFilter filter;
//	private TextViewer textViewer;
//	private WordTracker wordTracker;
	private static final int MAX_QUEUE_SIZE = 200;
	private SelfBizObjInfo value = null;
	
	private String bizobjId;
	private String fieldsValue;

	public SelBizObjInfoDialog(Shell parentShell, String bizobjId, String fieldsValue) {
		super(parentShell);
		setHelpAvailable(false);
		setTitleImage(FixUtil.getImageFromURL(FixImageProvider.CREATE_FILE_DIALOG));
		this.fieldsValue = fieldsValue;
		this.bizobjId = bizobjId;
	}

//	private String[] getDataSourceArray() {
//		String[] dbSourceArray = null;
//		// dataSourceList = StudioInterface.getDataSource();
//
//		dataSourceList = StudioInterface.getDataSource(FixUtil
//				.getFileFullPathFromActiveEditor().substring(1));
//
//		if (dataSourceList != null) {
//			dbSourceArray = new String[dataSourceList.size()];
//			for (int i = 0; i < dataSourceList.size(); i++) {
//				dbSourceArray[i] = dataSourceList.get(i).getId();
//			}
//		}
//
//		return dbSourceArray;
//
//	}

	@Override
	protected Control createDialogArea(Composite parent) {

		this.setTitle("业务对象字段");
		this.setMessage("选择业务对象字段");

		Composite container = (Composite) super.createDialogArea(parent);
		this.createControl(container);

		return super.createDialogArea(parent);
	}

	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));
		// new Label(composite, SWT.NULL).setText("显示名称");
		// objectNameText=new Text(composite,SWT.NULL|SWT.BORDER);
		// objectNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// new Label(composite, SWT.NULL);

		// new Label(composite, SWT.NULL).setText("数据源");

		// dataSourceCombo = new Combo(composite, SWT.NONE|SWT.READ_ONLY);

		// dataSourceCombo.setItems(getDataSourceArray());

		// GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		// gridData.grabExcessHorizontalSpace = true;

		// dataSourceCombo.setLayoutData(gridData);
		// new Label(composite, SWT.NULL);

		new Label(composite, SWT.NULL).setText("业务对象名");
		metaDataCombo = new ComboViewer(composite, SWT.NONE | SWT.READ_ONLY);
		metaDataCombo.setContentProvider(new IStructuredContentProvider() {

			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
				// TODO Auto-generated method stub

			}

			public void dispose() {
				// TODO Auto-generated method stub

			}

			public Object[] getElements(Object inputElement) {
				if (inputElement != null) {
					if (inputElement instanceof List) {
						return ((List) inputElement).toArray();
					}
				}
				// TODO Auto-generated method stub
				return null;
			}
		});
		metaDataCombo.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return (String) element;
			}
		});

		metaDataCombo.getCombo().setLayoutData(
				new GridData(GridData.FILL_HORIZONTAL));

		metaDataCombo.setInput(getAllBizId());
		
//		if(CustomDialogCellEditor.bizobjId == null) {
//			metaDataCombo.setSelection(new StructuredSelection(bizobjId));
//		} else {
//			metaDataCombo.setSelection(new StructuredSelection(CustomDialogCellEditor.bizobjId));
//		}
		
		metaDataCombo.getCombo().setEnabled(false);

		// StudioInterface.get

		new Label(composite, SWT.NULL);

		new Label(composite, SWT.NULL).setText("");
		viewer = new TableViewer(composite, SWT.CHECK | SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		viewer.getTable().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		checkTableViewer = new CheckboxTableViewer(viewer.getTable());
		createAttrColumns(composite, viewer);
		viewer.setColumnProperties(BizObjContants.TABLE_COLUMNS);
		viewer.setContentProvider(new ArrayContentProvider());
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		GridData gd = new GridData(SWT.LEFT, SWT.FILL, false, true);

		gd.heightHint = 200;
		gd.minimumHeight = 50;
		table.setLayoutData(gd);
		table.addListener(SWT.MeasureItem, new Listener() { // TODO 修改行高度
					public void handleEvent(Event event) {
						event.width = table.getGridLineWidth();
						// 设置宽度
						event.height = (int) Math.floor(event.gc
								.getFontMetrics().getHeight() * 1.5);

					}
				});

		addListeners();

		// setControl(composite);
		
		procEntities();
	}

	private List<String> getAllBizId() {
		List<String> allBizId = new ArrayList<String>();

		List<BizobjEntity> bizObjEntitys = CacheUtil.getAllBizobjRealPath();

		for (BizobjEntity entity : bizObjEntitys) {
			allBizId.add(entity.getId());
		}

		return allBizId;

	}

	/**
	 * 获取所选的表名称
	 * 
	 * @return
	 */
	public String getTableName() {
		ISelection selection = metaDataCombo.getSelection();
		if (selection == null)
			return null;
		String selectString = (String) ((StructuredSelection) selection)
				.getFirstElement();

		return selectString;

	}

//	protected String findMostRecentWord(int startSearchOffset) {
//		int currOffset = startSearchOffset;
//		char currChar;
//		String word = "";
//		try {
//			while (currOffset > 0
//					&& !Character.isWhitespace(currChar = textViewer
//							.getDocument().getChar(currOffset))) {
//				word = currChar + word;
//				currOffset--;
//			}
//			return word;
//		} catch (BadLocationException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

	protected boolean isWhitespaceString(String string) {
		StringTokenizer tokenizer = new StringTokenizer(string);
		// if there is at least 1 token, this string is not whitespace
		return !tokenizer.hasMoreTokens();
	}

	private void createAttrColumns(final Composite parent,
			final TableViewer viewer) {

		TableViewerColumn col = createTableViewerColumn(viewer,
				BizObjContants.COLUMN_PROPERTIYE_NAMES[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				ObjColumnTo p = (ObjColumnTo) element;
				return p.getId();
			}
		});

		col = createTableViewerColumn(viewer,
				BizObjContants.COLUMN_PROPERTIYE_NAMES[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				ObjColumnTo p = (ObjColumnTo) element;
				return p.getName();
			}
		});

		col = createTableViewerColumn(viewer,
				BizObjContants.COLUMN_PROPERTIYE_NAMES[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				ObjColumnTo p = (ObjColumnTo) element;
				return p.getDataType();
			}
		});

	}

	private TableViewerColumn createTableViewerColumn(TableViewer viewer,
			String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;

	}

	private void addListeners() {
		// dataSourceCombo.addListener(SWT.Modify, this);
		metaDataCombo.getCombo().addListener(SWT.Modify, this);

	}

	public void handleEvent(Event event) {

//		viewer.refresh();
//		String messages = null;
//		StringBuffer sb = new StringBuffer();
//
//		// TODO Auto-generated method stub
//		if ((event.widget == dataSourceCombo)) {
//
//			try {
//				procMetas();
//				// setErrorMessage(null);
//			} catch (Exception e) {
//				messages = "数据库连接异常";
//				viewer.setInput(null);
//				if (sb.length() > 0)
//					sb.append(",");
//				sb.append(messages);
//
//			}
//			viewer.getTable().removeAll();
//
//		}
//
//		if ((event.widget == metaDataCombo.getCombo())) {
//			procEntities();
//
//		}
//		if (sb.length() > 0)
//			messages = sb.toString();
//		setErrorMessage(messages);

	}

	private boolean existFile() {

		return FixUtil.hasSameBizobj(this.getTableName());

	}

	private void procEntities() {
		int selectedIndex = metaDataCombo.getCombo().getSelectionIndex();
		if (selectedIndex != -1) {
			// List<DBEntity> list = null;
			// DatabaseEntity dbEntity = StudioInterface.getDataSource(
			// selectedDBSource, selectedDataType, metaDataCombo
			// .getCombo().getText());
			//
			// if (dbEntity != null) {
			// List<Map<String, Object>> rowData = dbEntity.getRowsData();
			// list = new ArrayList<DBEntity>();
			// if (rowData != null) {
			// for (int i = 0; i < rowData.size(); i++) {
			// Map<String, Object> data = rowData.get(i);
			// String name = (String) data
			// .get(DatabaseEntity.COLUMN_NAME);
			// String type = (String) data
			// .get(DatabaseEntity.DATA_TYPE);
			// DBEntity e = new DBEntity(name, type);
			// list.add(e);
			// }
			// }
			// }
			//
			// viewer.setInput(list);
			// if (list != null)
			// checkTableViewer.setAllChecked(true);
			
			String bizObjId = metaDataCombo.getCombo().getText();

			// String pathString =
			// StudioInterface.getBizobjRealPathFromBizobjId(bizObjId);
			BizObjTo bizObjTo = null;
			try {
				// FileInputStream inputStream = new FileInputStream(new
				// File(pathString));

				bizObjTo = FixUtil.getBizObjById(bizObjId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				FixLoger.error(e.getMessage());
			}

			if (bizObjTo != null) {
				viewer.setInput(bizObjTo.getColumns());
			}

			//如果已经存在所有的字段需要勾起来
			if(fieldsValue != null && !fieldsValue.equals("")) {
				TableItem[] items = checkTableViewer.getTable().getItems();
				String[] array = fieldsValue.split(",");
				for (int i = 0; i < array.length; i++) {
					for (int j = 0; j < items.length; j++) {
						ObjColumnTo to = (ObjColumnTo) checkTableViewer.getElementAt(j);
						if(to.getId().equals(array[i])) {
							checkTableViewer.setChecked(to, true);
						}
					}
				}
			}
		}
	}

//	private void procMetas() {
//		int selectedIndex = dataSourceCombo.getSelectionIndex();
//		if (selectedIndex != -1) {
//			// metaDataCombo.getCombo().removeAll();
//
//			selectedDBSource = dataSourceList.get(selectedIndex);
//
//			// String bizType = ((CreateBizObjWizard) getWizard()).getBizObj()
//			// .getObjtype().name();
//			// if (bizType.equals(BizObjContants.BIZ_TYPE_TABLE_ID))// 表
//			selectedDataType = 1;
//			// else if (bizType.equals(BizObjContants.BIZ_TYPE_VIEW_ID))// 视图
//			// selectedDataType = 2;
//			// if (bizType.equals(BizObjContants.BIZ_TYPE_PROC_ID))// 存储过程
//			// selectedDataType = 3;
//
//			String[] meta = null;
//			List<String> list = StudioInterface.getDataSource(selectedDBSource,
//					selectedDataType);
//
//			metaDataCombo.setInput(list);
//			metaDataCombo.refresh();
//			// metaDataCombo.setInput(meta);
//
//		}
//
//	}

	public String getDataSource() {
		return dataSourceCombo.getText();
	}

	public ObjColumnTo[] getColumn() {
		Object[] o = this.checkTableViewer.getCheckedElements();
		List<ObjColumnTo> list = new ArrayList<ObjColumnTo>();
		for (int i = 0; i < o.length; i++)
			list.add((ObjColumnTo) o[i]);

		return (ObjColumnTo[]) list.toArray(new ObjColumnTo[] {});

	}

	public String getColumns() {
		ObjColumnTo[] dbe = this.getColumn();
		String columns = "";
		for (int i = 0; i < dbe.length; i++) {
			String tmp = dbe[i].getId();
			columns += "," + tmp;
		}

		if (!columns.equals("")) {
			columns = columns.substring(1);
		}

		return columns;

	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			this.value = new FormConst.SelfBizObjInfo();

			// this.value.DataSource = this.getDataSource();
			this.value.BizObjName = getTableName();
			// this.value.Columns = this.getColumn();
			this.value.ColumnNames = this.getColumns();
		} else {
			this.value = null;
		}
		super.buttonPressed(buttonId);
	}

	@Override
	protected Control createContents(Composite parent) {

		return super.createContents(parent);
	}

	@Override
	protected void okPressed() {
		super.okPressed();
	}

	public SelfBizObjInfo getValue() {
		return this.value;
	}

}
