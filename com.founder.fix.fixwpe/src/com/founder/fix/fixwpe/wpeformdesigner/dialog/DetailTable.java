package com.founder.fix.fixwpe.wpeformdesigner.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

import com.founder.fix.apputil.to.bizobj.BizObjTo;
import com.founder.fix.apputil.to.bizobj.ObjDataRelationTo;
import com.founder.fix.apputil.util.BizObjectUtil;
import com.founder.fix.base.wpe.FormPageUtil;
import com.founder.fix.ocx.FixUtil;
import com.founder.fix.ocx.Utility;

public class DetailTable extends TitleAreaDialog {
	private Text text_col;
	private Text text_row;
	private Combo combo_bizobj;
	
	private Button btnCheckButton_AI;
	private Button btnCheckButton_detail;
	
	public Boolean aISelectionState = false;
	
	
	List<ObjDataRelationTo> dataRelations;
	
	private int rowCount = 3;
	private int colCount= 3;
	private String bizObjName = "";
	
	
	public int getRowCount() {
		return rowCount;
	}

	
	public int getColCount() {
		return colCount;
	}

	public String getBizObjName() {
		return bizObjName;
	}

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public DetailTable(Shell parentShell) {
		super(parentShell);
		setHelpAvailable(false);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		
		setTitle("插入明细表");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setVisible(true);
		container.setLayout(new GridLayout(4, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		btnCheckButton_detail = new Button(container, SWT.CHECK);
		btnCheckButton_detail.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BizObjTo bizObjTo = Utility.getBizObjToFromHtmlPath(
						FormPageUtil.currentFormPagePath);
				dataRelations = bizObjTo.getDataRelations();
				if(dataRelations.size()>0){
					if(btnCheckButton_detail.getSelection()){
						btnCheckButton_AI.setVisible(true);
						int length = dataRelations.size();
						String []items = new String[length];
						for(int i=0;i<length;i++){
							items[i] = (dataRelations.get(i).getId()+
									"("+dataRelations.get(i).getName()+")");
						}
						combo_bizobj.setItems(items);
					}else{
						btnCheckButton_AI.setVisible(false);
					}
				}else{
					setMessage("该业务对象无关联对象!");
				}
			}
		});
		btnCheckButton_detail.setText("明细表");
		
		btnCheckButton_AI = new Button(container, SWT.CHECK);
		btnCheckButton_AI.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnCheckButton_AI.getSelection()){
					aISelectionState = true;
				}else{
					aISelectionState = false;
				}
			}
		});
		btnCheckButton_AI.setVisible(false);
		btnCheckButton_AI.setText("智能");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("列数");
		
		text_col = new Text(container, SWT.BORDER);
		text_col.setText("3");
		text_col.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				try{
					colCount = Integer.valueOf(text_col.getText());
				}catch(Exception exception){
					setErrorMessage("输入的不合法!");
					colCount = 0;
				}
			}
		});
		GridData gd_text_col = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		gd_text_col.widthHint = 311;
		text_col.setLayoutData(gd_text_col);
		
		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("行数");
		
		text_row = new Text(container, SWT.BORDER);
		text_row.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				try{
					rowCount = Integer.valueOf(text_row.getText());
				}catch(Exception exception){
					setErrorMessage("输入的不合法!");
					colCount = 0;
				}
			}
		});
		text_row.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		text_row.setText("3");
		Label lblNewLabel_2 = new Label(container, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("明细表");
		
		combo_bizobj = new Combo(container, SWT.NONE);
		combo_bizobj.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String text = combo_bizobj.getText().trim();
				bizObjName = text.substring(0, text.indexOf("("));
				
				try {
					BizObjTo bizObj = FixUtil.getBizObjById(bizObjName);
					text_col.setText(bizObj.getColumns().size()+"");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		combo_bizobj.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		return area;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button_ok = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		button_ok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		Button button_cancel = createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		button_cancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(300, 300);
	}

}
