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
public class SelectBizobjAndViewWizard extends Wizard {
	
	private SelectBizobjWizardPage selectBizobjWizardPage;
	
	private SelectBizobjViewWizardPage selectBizobjViewWizardPage;
	
	private String[] values;
	
	private String value;

	/**
	 * 
	 */
	public SelectBizobjAndViewWizard(String value) {
		if(value != null && !value.equals("")) {
			this.values = value.split("\\.");
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizardSelectBizobjAndViewWizard.java.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		value = selectBizobjWizardPage.getValue().getId() + "." + selectBizobjViewWizardPage.getValue().getId();
		return true;
	}

	@Override
	public boolean canFinish() {
		//当没有到最后一页时‘完成’按钮不可用
		if(this.getContainer().getCurrentPage() != selectBizobjViewWizardPage) { //最后一个页面
			return false;
		}
		return super.canFinish();
	}
	
	@Override
	public void addPages() {
		//实例化向导页面
		selectBizobjWizardPage = new SelectBizobjWizardPage("selectBizobjWizardPage", "选择业务对象",
				FixUtil.getImageDescFromURL(FixImageProvider.CREATE_FILE_DIALOG), true,
				(values == null || values.length == 1) ? "" : values[0]);
		
		selectBizobjViewWizardPage = new SelectBizobjViewWizardPage("selectBizobjViewWizardPage", "选择视图", 
				FixUtil.getImageDescFromURL(FixImageProvider.CREATE_FILE_DIALOG),
				(values == null || values.length == 1) ?  "" : values[1]);
		
		addPage(selectBizobjWizardPage);
		addPage(selectBizobjViewWizardPage);
		super.addPages();
	}

	public SelectBizobjViewWizardPage getSelectBizobjViewWizardPage() {
		return selectBizobjViewWizardPage;
	}

	public void setSelectBizobjViewWizardPage(
			SelectBizobjViewWizardPage selectBizobjViewWizardPage) {
		this.selectBizobjViewWizardPage = selectBizobjViewWizardPage;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
