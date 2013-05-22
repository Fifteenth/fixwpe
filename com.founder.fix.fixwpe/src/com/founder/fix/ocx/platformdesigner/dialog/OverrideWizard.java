/**
 * Copyright c FOUNDER CORPORATION 2011 All Rights Reserved.
 * OverrideWizardDialog.java
 */
package com.founder.fix.ocx.platformdesigner.dialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;


/**
 * [类名]<br>
 * OverrideWizardDialog.java<br>
 * <br>
 * [功能概要]<br>
 *
 * <br>
 * [变更履历]<br>
 *
 * <br>
 * 2011-6-21 ver1.0 <br>
 * <br>
 *
 * @作者 wangzhiwei
 *
 */

public class OverrideWizard extends WizardDialog {

	private Wizard wizard;
	
	/**
	 * 构造器
	 */
	public OverrideWizard(Shell parentShell, IWizard newWizard) {
		super(parentShell, newWizard);
		setHelpAvailable(false);
		this.wizard = (Wizard) newWizard;
	}

	/**
	 * 重写createContents用以改变按钮文字为中文
	 */
	@Override
	protected Control createContents(Composite parent) {
		 Control c = super.createContents(parent);
		 getButton(IDialogConstants.FINISH_ID).setText("完成"); //完成按钮
	     getButton(IDialogConstants.CANCEL_ID).setText("取消"); //取消按钮
	     try {
	    	 getButton(IDialogConstants.BACK_ID).setText("上一步"); //上一步
			 getButton(IDialogConstants.NEXT_ID).setText("下一步"); //下一步
		} catch (Exception e) {}
	     return c;
	}
	
	/**
	 * 返回值
	 * @return
	 */
	public String getValue() {
		return ((SelectBizobjAndViewWizard) wizard).getValue();
	}
	
	/**
	 * 返回值
	 * @return
	 */
	public String getServiceValue() {
		return ((SelectBizobjAndServiceWizard) wizard).getValue();
	}
	
	
	/**
	 * 返回值
	 * @return
	 */
	public String getBizobjAndMethodValue() {
		return ((SelectBizobjAndMethodWizard) wizard).getValue();
	}
	
}
