/**
 * Copyright c FOUNDER CORPORATION 2011 All Rights Reserved.
 * FixLoger.java
 */
package com.founder.fix.ocx.platformdesigner.interfaces;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.founder.fix.ocx.platformdesigner.views.ConsoleView;


/**
 * [类名]<br>
 * FixLoger.java<br>
 * <br>
 * [功能概要]<br>
 *
 * <br>
 * [变更履历]<br>
 *
 * <br>
 * 2011-7-3 ver1.0 <br>
 * <br>
 *
 * @作者 wangzhiwei
 *
 */

public class FixLoger {
	
	private static ConsoleView consoleView; 
	
	private static Color red = new Color(null, 255, 0, 0);
	
	private static Color blue = new Color(null, 0, 0, 255);
	
	private static Color black = new Color(null, 0, 0, 0);
	
	private static final String infoPrefix = " |INFOM| ";
	
	private static final String debugPrefix = " |DEBUG| ";
	
	private static final String errorPrefix = " |ERROR| ";
	
	private static StyleRange[] ranges = new StyleRange[3];
	
	private static MessageConsoleStream consoleStream = null;  //控制台输出流

	/**
	 * 构造器
	 */
	private FixLoger() {
		// TODO Auto-generated constructor stub
	}
	
	public static void fixInfo(Exception e) {
		e.printStackTrace();
		MessageDialog.openError(null, "错误", e.toString());
	}
	
	/**
	 * 初始化
	 */
	private static void initLoger() {
		//控制台管理
		IConsoleManager manager = (IConsoleManager) ConsolePlugin.getDefault().getConsoleManager(); 
		//控制台对象
		IConsole console = null;  
		//循环搜索控制台对象
		IConsole[] consoles = manager.getConsoles();
		if(consoles != null && consoles.length > 0) {
			for (int i = 0; i < consoles.length; i++) {
				console = consoles[i];
				if(console instanceof MessageConsole) {
					consoleStream = ((MessageConsole) console).newMessageStream();
					break;
				}
			}
		} else {
			console = new MessageConsole("平台输出信息", null);
			consoleStream = ((MessageConsole) console).newMessageStream();
			manager.addConsoles(new IConsole[] { console });
			manager.showConsoleView(console);
		}
	}
	
	/**
	 * 格式化日期并返回
	 * @return
	 */
	private static String formatDate() {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateformat.format(new Date());
	}

	/**
	 * 控制台信息输出
	 * @param info
	 */
	public static void info(String info) {
		initLoger();
		if(consoleStream != null) {
			//设置颜色
			consoleStream.setColor(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
			//打印输出
			consoleStream.println(formatDate() + infoPrefix + info); 
		}
	}
	
	/**
	 * 控制台调试输出
	 * @param debug
	 */
	public static void debug(String debug) {
		initLoger();
		if(consoleStream != null) {
			//设置颜色
			consoleStream.setColor(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
			//打印输出
			consoleStream.println(formatDate() + debugPrefix + debug); 
		}
	}
	
	/**
	 * 控制台错误输出
	 * @param error
	 */
	public static void error(String error) {
		initLoger();
		if(consoleStream != null) {
			//设置颜色
			consoleStream.setColor(Display.getDefault().getSystemColor(SWT.COLOR_RED));
			//打印输出
			consoleStream.println(formatDate() + errorPrefix + error); 
		}
	}
	
	/**
	 * 处理ranges数组来设置各行文本颜色
	 */
	private static void formateInfomation(StyledText text, String string, String prefix) {
		String textStr = text.getText().trim();
		
		//设置字符串
		if(textStr == null || textStr.equals("")) {
			textStr += formatDate() + prefix + string;
		} else {
			textStr += "\n" + formatDate() + prefix + string;
		}
		
		text.setText(textStr);
		
		String tmpStr = new String();
		String[] array = textStr.split("\n");
		ranges = new StyleRange[array.length];
		
		//封装各种颜色
		for (int i = 0; i < array.length; i++) {
			if(array[i].indexOf("INFO") != -1) {
				ranges[i] = new StyleRange(tmpStr.length(), array[i].length(), black, null);
			} else if(array[i].indexOf("DEBUG") != -1) {
				ranges[i] = new StyleRange(tmpStr.length(), array[i].length(), blue, null);
			} else if(array[i].indexOf("ERROR") != -1) {
				ranges[i] = new StyleRange(tmpStr.length(), array[i].length(), red, null);
			}
			//临时字符串用于取长度
			tmpStr += array[i] + "\n";
		}
		
		//设置生效
		text.replaceStyleRanges(0, textStr.length(), ranges);
		
		//自动定位最后一行
		text.setTopIndex(text.getLineCount()); 
	}
}
