/**
 * Copyright c FOUNDER CORPORATION 2011 All Rights Reserved.
 * ConsoleView.java
 */
package com.founder.fix.ocx.platformdesigner.views;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.part.ViewPart;

/**
 * [类名]<br>
 * ConsoleView.java<br>
 * <br>
 * [功能概要]<br>
 *
 * <br>
 * [变更履历]<br>
 *
 * <br>
 * 2011-6-20 ver1.0 <br>
 * <br>
 *
 * @作者 wangzhiwei
 *
 */
public class ConsoleView extends ViewPart {
	
	private StyledText text = null; //文本组件

	/**
	 * 
	 */
	public ConsoleView() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout()); //布局
		
		//初始化列表控件
		text = new StyledText(composite, SWT.MULTI | SWT.READ_ONLY | SWT.V_SCROLL | SWT.H_SCROLL); 
		text.setEditable(false); //只读
		
		//设置字体
		Font font = new Font(parent.getDisplay(), "Courier", 11, SWT.NONE);
		text.setFont(font);
		
		text.setBackground(new Color(null, 255, 255, 255)); //背景色白色
		
		//注册菜单
		fillContextMenu();
	}
	
	/**
	 * 注册菜单
	 */
	private void fillContextMenu() {
		//实例化自定义actionGroup
		ConsoleActionGroup group = new ConsoleActionGroup(text);
		//treeViewer和menu互为参数
		MenuManager manager = new MenuManager(); 
		Menu menu = manager.createContextMenu(text.getShell());
		//将menu放入treeViewer中
		text.setMenu(menu);
		//填充菜单
		group.fillContextMenu(manager);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public StyledText getText() {
		return text;
	}

	public void setText(StyledText text) {
		this.text = text;
	}

}
