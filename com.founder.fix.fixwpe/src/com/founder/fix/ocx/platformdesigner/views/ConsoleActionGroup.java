/**
 * Copyright c FOUNDER CORPORATION 2011 All Rights Reserved.
 * ConsoleActionGroup.java
 */
package com.founder.fix.ocx.platformdesigner.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.ui.actions.ActionGroup;


/**
 * [类名]<br>
 * ConsoleActionGroup.java<br>
 * <br>
 * [功能概要]<br>
 *
 * <br>
 * [变更履历]<br>
 *
 * <br>
 * 2011-7-6 ver1.0 <br>
 * <br>
 *
 * @作者 wangzhiwei
 *
 */

public class ConsoleActionGroup extends ActionGroup {
	
	private StyledText text = null; //文本组件

	/**
	 * 构造器
	 */
	public ConsoleActionGroup(StyledText text) {
		this.text = text;
	}

	/**
	 * 加入菜单
	 */
	@Override
	public void fillContextMenu(IMenuManager menu) {
		if(menu == null) {
			return;
		}
		
		//在这个action组添加一个action动作
		menu.add(new ClearConsoleAction(text, "清空控制台", null));
		
	}
}
