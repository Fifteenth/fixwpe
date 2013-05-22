/**
 * Copyright c FOUNDER CORPORATION 2011 All Rights Reserved.
 * ClearConsoleAction.java
 */
package com.founder.fix.ocx.platformdesigner.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.custom.StyledText;

/**
 * [类名]<br>
 * ClearConsoleAction.java<br>
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

public class ClearConsoleAction extends Action {
	
	private StyledText text = null; //文本组件

	/**
	 * 构造器
	 */
	public ClearConsoleAction() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 构造器
	 */
	public ClearConsoleAction(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 构造器
	 */
	public ClearConsoleAction(StyledText styledText, String text, ImageDescriptor image) {
		super(text, image);
		this.text = styledText;
	}

	/**
	 * 构造器
	 */
	public ClearConsoleAction(String text, int style) {
		super(text, style);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		//清空控制台信息
		text.setText("");
		
		super.run();
	}
}
