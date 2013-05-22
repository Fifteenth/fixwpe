/**
 * 
 */
package com.founder.fix.ocx.platformdesigner.dialog;

import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.founder.fix.base.platformdesigner.Entity.project.BizobjEntity;
import com.founder.fix.ocx.util.CacheUtil;
import com.founder.fix.ocx.util.WindowStyle;


/**
 * @author wangzhiwei
 *
 */
public class SelectBizobjWizardPage extends WizardPage {
	private static int[] bounds = { 160, 160 };
	
	private TableViewer viewer;
	
	private String bizobjValue;
	
	private BizObjFilter filter;
	
	private BizobjEntity value;
	
	private List<BizobjEntity> entities;
	
	private boolean showAllBizobjs;
	
	

	private String lastAction;

	/**
	 * @param pageName
	 */
	public SelectBizobjWizardPage(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param pageName
	 * @param title
	 * @param titleImage
	 */
	public SelectBizobjWizardPage(String pageName, String title,
			ImageDescriptor titleImage, boolean showAllBizobjs, String bizobjValue) {
		super(pageName, title, titleImage);
		this.showAllBizobjs = showAllBizobjs;
		this.bizobjValue = bizobjValue;
		
		
		this.lastAction = bizobjValue;//上一步操作
		
		//title信息
		setTitle(title);
		
		//消息信息
		setMessage("请选择业务对象", INFORMATION);
		
		//设置图片
		setImageDescriptor(titleImage);
	}
	
	

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		//创建一个底层的Composite，并使用GridLayout布局
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new FillLayout());
		
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
			if(bizobjValue.equals("SelectBizobjAndMethodWizard")){
				
			}
			else{
				searchText.setText(bizobjValue);
			}
			
		}
		
		searchText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				filter.setSearchText(searchText.getText());
				viewer.refresh();
				
				setPageComplete(false);
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
				// 设置宽度
				event.height = (int) Math.floor(event.gc.getFontMetrics().getHeight() * 1.5);
			}
		});
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				getFirstSelected();
				if (value != null) {
					
				}
			}

			@Override
			public void mouseDown(MouseEvent e) {
				//获取选择的对象
				getFirstSelected();
				if (value != null) {
					//将值交给下一个页面
					if(lastAction.equals("SelectBizobjAndMethodWizard")){
						((SelectBizobjAndMethodWizard) getWizard()).getSelectBizobjServiceWizardPage().setBizobjEntity(value);
					}
					else{
						((SelectBizobjAndViewWizard) getWizard()).getSelectBizobjViewWizardPage().setBizobjEntity(value);
					}
					setPageComplete(true);
				}
			}
		});
		
		viewer.setContentProvider(new ArrayContentProvider());
		
		//允许自定义数据的业务对象集合
		if(entities == null) {
			if(showAllBizobjs) {
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
				}
			}
		}

		//初始化的时候先使‘完成’按钮不可用
		setPageComplete(false); 
		
		//必须要的，将新的Composite放入
		setControl(container);
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
}
