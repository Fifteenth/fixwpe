/**
 * 
 */
package com.founder.fix.ocx.platformdesigner.dialog;

import org.eclipse.jface.wizard.Wizard;

import com.founder.fix.base.wpe.FixImageProvider;
import com.founder.fix.ocx.util.FixUtil;

/**
 * @author wangzhiwei
 *
 */
public class SelectBizobjAndServiceWizard extends Wizard {
	
	private SelectBizobjForServiceWizardPage selectBizobjWizardPage;
	
	private SelectBizobjServiceWizardPage selectBizobjServiceWizardPage;
	
	private String[] values;
	
	private String value;

	/**
	 * 
	 */
	public SelectBizobjAndServiceWizard(String value) {
		if(value != null && !value.equals("")) {
			this.values = value.split("\\.");
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		value = selectBizobjWizardPage.getValue().getId() + "." + selectBizobjServiceWizardPage.getValue().getId();
		return true;
	}

	@Override
	public boolean canFinish() {
		//当没有到最后一页时‘完成’按钮不可用
		if(this.getContainer().getCurrentPage() != selectBizobjServiceWizardPage) { //最后一个页面
			return false;
		}
		return super.canFinish();
	}
	
	@Override
	public void addPages() {
		//实例化向导页面
		selectBizobjWizardPage = new SelectBizobjForServiceWizardPage("selectBizobjForServiceWizardPage", "选择业务对象",
				FixUtil.getImageDescFromURL(FixImageProvider.CREATE_FILE_DIALOG), true,
				(values == null || values.length == 1) ? "" : values[0]);
		
		selectBizobjServiceWizardPage = new SelectBizobjServiceWizardPage("selectBizobjServiceWizardPage", "选择方法", 
				FixUtil.getImageDescFromURL(FixImageProvider.CREATE_FILE_DIALOG),
				(values == null || values.length == 1) ?  "" : values[1]);
		
		addPage(selectBizobjWizardPage);
		addPage(selectBizobjServiceWizardPage);
		super.addPages();
	}

	public SelectBizobjServiceWizardPage getSelectBizobjServiceWizardPage() {
		return selectBizobjServiceWizardPage;
	}

	public void setSelectBizobjServiceWizardPage(
			SelectBizobjServiceWizardPage selectBizobjServiceWizardPage) {
		this.selectBizobjServiceWizardPage = selectBizobjServiceWizardPage;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
