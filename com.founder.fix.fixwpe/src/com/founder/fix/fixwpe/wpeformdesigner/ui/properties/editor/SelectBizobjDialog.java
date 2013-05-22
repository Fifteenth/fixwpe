/**
 * Copyright c FOUNDER CORPORATION 2011 All Rights Reserved.
 * SelectBizobjDialog.java
 */
package com.founder.fix.fixwpe.wpeformdesigner.ui.properties.editor;

import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.founder.fix.base.platformdesigner.Entity.project.BizobjEntity;
import com.founder.fix.base.wpe.FixImageProvider;
import com.founder.fix.ocx.platformdesigner.dialog.BizObjFilter;
import com.founder.fix.ocx.platformdesigner.dialog.SelectBizobjSorter;
import com.founder.fix.ocx.util.CacheUtil;
import com.founder.fix.ocx.util.FixUtil;
import com.founder.fix.ocx.util.WindowStyle;


/**
 * [类名]<br>
 * SelectBizobjDialog.java<br>
 * <br>
 * [功能概要]<br>
 *
 * <br>
 * [变更履历]<br>
 *
 * <br>
 * 2011-9-19 ver1.0 <br>
 * <br>
 *
 * @作者 wangzhiwei
 *
 */

public class SelectBizobjDialog extends TitleAreaDialog {
	private static int[] bounds = { 180, 180 };
	
	private TableViewer viewer;
	
	private BizobjEntity value;
	
	private BizObjFilter filter;
	
	private List<BizobjEntity> entities;
	
	private String bizobjValue;
	
	private boolean showAllBizobjs;

	public SelectBizobjDialog(Shell parentShell, boolean showAllBizobjs) {
		super(parentShell);
		setHelpAvailable(false);
		setTitleImage(FixUtil.getImageFromURL(FixImageProvider.CREATE_FILE_DIALOG));
		this.showAllBizobjs = showAllBizobjs;
	}
	
	public SelectBizobjDialog(Shell parentShell, List<BizobjEntity> entities) {
		super(parentShell);
		setHelpAvailable(false);
		this.entities = entities;
		setTitleImage(FixUtil.getImageFromURL(FixImageProvider.CREATE_FILE_DIALOG));
	}
	
	public SelectBizobjDialog(Shell parentShell, String bizobjValue,
			boolean showAllBizobjs) {
		super(parentShell);
		setHelpAvailable(false);
		setTitleImage(FixUtil.getImageFromURL(FixImageProvider.CREATE_FILE_DIALOG));
		this.showAllBizobjs = showAllBizobjs;
		this.bizobjValue = bizobjValue;
	}

	/**
	 * 设置对话框大小
	 */
	protected Point getInitialSize() {
		return new Point(539, 520);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		GridLayout layout = new GridLayout();
		layout.marginLeft = 20;
		layout.marginTop = 10;
		layout.marginRight = 20;
		container.setLayout(layout);

		Composite client = new Composite(container, SWT.NULL);
		GridLayout layoutClient = new GridLayout();
		layoutClient.marginTop = 4;
		layoutClient.horizontalSpacing = 20;
		layoutClient.verticalSpacing = 20;
		layoutClient.numColumns = 2;
		client.setLayout(layoutClient);

		Label searchLabel = new Label(client, SWT.NONE);
		searchLabel.setText("搜索: ");
		final Text searchText = new Text(client, SWT.BORDER | SWT.SEARCH);
		searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		
		if(bizobjValue != null && !bizobjValue.equals("")) {
			searchText.setText(bizobjValue);
		}
		
		searchText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				filter.setSearchText(searchText.getText());
				viewer.refresh();
			}
		});
		
//		Button newObjBtn = new Button(client, SWT.PUSH);
//		newObjBtn.setText("创建对象");
//		newObjBtn.addSelectionListener(new SelectionListener() {
//
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				CreateBizObjWizard wizard = new CreateBizObjWizard(
//						StudioInterface
//								.getViewPartFromViewId(FixPerspective.PROJECT_VIEW), bizObjId);
//				WizardDialog dialog = new WizardDialog(getShell(), wizard);
//				dialog.setBlockOnOpen(true);
//				int returnCode = dialog.open();
//				if (returnCode == Dialog.OK) {
//					viewer.refresh();
//
//					List<BizobjEntity> lists = CacheUtil.getAllBizobjRealPath();
//					for (BizobjEntity be : lists) {
//						if (be.getId().equals(wizard.getBizObj().getId())) {
//							viewer.setSelection(new StructuredSelection(
//									new Object[] { be }), true);
//							value = be;
//							getButton(IDialogConstants.OK_ID).setEnabled(true);
//							break;
//						}
//					}
//				}
//			}
//
//			public void widgetDefaultSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//		});
		
		new Label(client, SWT.NULL);
		viewer = new TableViewer(client, /* SWT.CHECK | SWT.MULTI*/
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		filter = new BizObjFilter();
		viewer.addFilter(filter);

		createAttrColumns(client, viewer);
		
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		GridData gd = new GridData(SWT.LEFT, SWT.FILL, false, true);

		gd.heightHint = 240;
		gd.minimumHeight = 50;
		// gd.horizontalSpan=2;
		table.setLayoutData(gd);
		
		table.addListener(SWT.MeasureItem, new Listener() { // TODO 修改行高度
			public void handleEvent(Event event) {
				event.width = table.getGridLineWidth();
				// 设置宽度
				event.height = (int) Math.floor(event.gc.getFontMetrics().getHeight() * 1.5);
			}
		});
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				getFirstSelected();
				
				if(value != null && value.getId().equals(bizobjValue)) {
					return;
				}

				if (value != null)
					okPressed();
			}

			@Override
			public void mouseDown(MouseEvent e) {
				getFirstSelected();
				if(value != null && value.getId().equals(bizobjValue)) {
					getButton(IDialogConstants.OK_ID).setEnabled(false);
					return;
				} 
				getButton(IDialogConstants.OK_ID).setEnabled(value != null);
			}
		});
		
		viewer.setContentProvider(new ArrayContentProvider());
		
		//允许自定义数据的业务对象集合
		if(entities == null) {
			if(showAllBizobjs) {
				// 初始化所有业务对象的全路径放入缓存
				CacheUtil.initAllBizobjRealPath2Cache();
				
				viewer.setInput(CacheUtil.getAllBizobjRealPath());
			}
		} else {
			viewer.setInput(entities);
		}
		
		//置中
		WindowStyle.setCenter(getShell());

		setTitle("选择业务对象");

		//如果已经存在业务对象需要选中该业务对象
		if(bizobjValue != null && !bizobjValue.equals("")) {
			for (int i = 0; i < viewer.getTable().getItems().length; i++) {
				TableItem item = viewer.getTable().getItems()[i];
				if(item.getText().equals(bizobjValue)) {
					viewer.setSelection(new StructuredSelection(viewer.getElementAt(i)));
					break;
				}
			}
		}
		
		return container;
	}

	/**
	 * 获取表中选中的对象
	 */
	public void getFirstSelected() {
		ISelection sel = viewer.getSelection();
		if (sel == null)
			return;
		Object[] objs = ((IStructuredSelection) sel).toArray();
		if (objs == null || objs.length == 0)
			return;
		value = (BizobjEntity) objs[0];
	}

	/**
	 * 处理列
	 * @param parent
	 * @param viewer
	 */
	private void createAttrColumns(final Composite parent,
			final TableViewer viewer) {

		TableViewerColumn col = createTableViewerColumn(viewer, "ID", bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				BizobjEntity p = (BizobjEntity) element;
				return p.getId();
			}
		});
		col.getColumn().addSelectionListener(new SelectionListener() {
			boolean asc = true;  
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.setSorter(asc ? SelectBizobjSorter.ID_ASC
						: SelectBizobjSorter.ID_DESC); 
                asc = !asc; 
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		col = createTableViewerColumn(viewer, "名称", bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				BizobjEntity p = (BizobjEntity) element;
				return p.getName();
			}
		});
		col.getColumn().addSelectionListener(new SelectionListener() {
			boolean asc = true;  
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.setSorter(asc ? SelectBizobjSorter.NAME_ASC
						: SelectBizobjSorter.NAME_DESC); 
                asc = !asc; 
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	/**
	 * 返回选中的对象
	 * @return
	 */
	public BizobjEntity getValue() {
		return value;
	}

	/**
	 * 创建列
	 * @param viewer
	 * @param title
	 * @param bound
	 * @param colNumber
	 * @return
	 */
	private TableViewerColumn createTableViewerColumn(TableViewer viewer,
			String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	/**
	 * 修改button的名称
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		// create OK and Cancel buttons by default
		createButton(parent, IDialogConstants.OK_ID, "确定", true);
		createButton(parent, IDialogConstants.CANCEL_ID, "关闭", false);
		getButton(IDialogConstants.OK_ID).setEnabled(false);
	}

	// 处理点击完成按钮
	@Override
	protected void okPressed() {
		super.okPressed();
	}

}
