/**
 * 
 */
package com.founder.fix.ocx.platformdesigner.dialog;

import java.util.ArrayList;
import java.util.Iterator;
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

import com.founder.fix.apputil.to.bizobj.ObjServiceTo;
import com.founder.fix.base.platformdesigner.Entity.project.BizobjEntity;
import com.founder.fix.ocx.util.AppUtil;
import com.founder.fix.ocx.util.WindowStyle;

/**
 * @author wangzhiwei
 *
 */
public class SelectBizServiceDialog extends TitleAreaDialog {
	
	private static int[] bounds = { 180, 180 };
	
	private TableViewer viewer;
	
	private BizobjEntity value;
	
	private BizObjFilter filter;
	
	private List<BizobjEntity> entities;
	
	private String serviceValue;

	/**
	 * @param parentShell
	 */
	public SelectBizServiceDialog(Shell parentShell, String bizobjValue,
			String serviceValue) {
		super(parentShell);
		setHelpAvailable(false);
		this.serviceValue = serviceValue;
		
		//根据业务对象返回方法列表
		List<ObjServiceTo> list = AppUtil.getObjServicesFromBizobjId(bizobjValue);
		entities = changeType(list);
	}
	
	private List<BizobjEntity> changeType(List<ObjServiceTo> list) {
		if(list == null || list.size() == 0) {
			return null;
		}
		List<BizobjEntity> entities = new ArrayList<BizobjEntity>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			ObjServiceTo ObjService = (ObjServiceTo) iterator.next();
			
			BizobjEntity bizobjEntity = new BizobjEntity();
			bizobjEntity.setId(ObjService.getId());
			bizobjEntity.setName(ObjService.getName());
			
			entities.add(bizobjEntity);
		}

		return entities;
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
		
		if(serviceValue != null && !serviceValue.equals("")) {
			searchText.setText(serviceValue);
		}
		
		searchText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				filter.setSearchText(searchText.getText());
				viewer.refresh();
			}
		});
		
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
				// 设置高度
				event.height = (int) Math.floor(event.gc.getFontMetrics().getHeight() * 1.5);
			}
		});
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				getFirstSelected();
				if (value != null)
					okPressed();
			}

			@Override
			public void mouseDown(MouseEvent e) {
				getFirstSelected();
				getButton(IDialogConstants.OK_ID).setEnabled(value != null);
			}
		});
		
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setInput(entities);
		
		//置中
		WindowStyle.setCenter(getShell());

		setTitle("选择业务对象的方法");

		//如果已经存在该方法需要选中该业务对象
		if(serviceValue != null && !serviceValue.equals("")) {
			for (int i = 0; i < viewer.getTable().getItems().length; i++) {
				TableItem item = viewer.getTable().getItems()[i];
				if(item.getText().equals(serviceValue)) {
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

		col = createTableViewerColumn(viewer, "名称", bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				BizobjEntity p = (BizobjEntity) element;
				return p.getName();
			}
		});
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
	
	/**
	 * 返回选中的对象
	 * @return
	 */
	public BizobjEntity getValue() {
		return value;
	}
}
