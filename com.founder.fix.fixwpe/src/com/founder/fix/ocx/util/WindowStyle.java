/**
 * Copyright c FOUNDER CORPORATION 2011 All Rights Reserved.
 * WindowStyle.java
 */
package com.founder.fix.ocx.util;

import java.awt.Toolkit;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * [类名]<br>
 * WindowStyle.java<br>
 * <br>
 * [功能概要]<br>
 *
 * <br>
 * [变更履历]<br>
 *
 * <br>
 * 2011-7-13 ver1.0 <br>
 * <br>
 *
 * @作者 wangzhiwei
 *
 */

public class WindowStyle {

	/**
	 * 构造器
	 */
	private WindowStyle() {
		// TODO Auto-generated constructor stub
	}

	/** 
     * 设置窗口位于屏幕中间 
     * @param shell 要调整位置的窗口对象 
     */  
    public static void setCenter(Shell shell)  
    {  
        //获取屏幕高度和宽度  
        int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;  
        int screenW = Toolkit.getDefaultToolkit().getScreenSize().width;  
        //获取对象窗口高度和宽度  
        int shellH = shell.getBounds().height;  
        int shellW = shell.getBounds().width;  
          
        //如果对象窗口高度超出屏幕高度，则强制其与屏幕等高  
        if(shellH > screenH)  
            shellH = screenH;  
          
        //如果对象窗口宽度超出屏幕宽度，则强制其与屏幕等宽  
        if(shellW > screenW)  
            shellW = screenW;  
          
        //定位对象窗口坐标  
        shell.setLocation(((screenW - shellW) / 2), ((screenH - shellH) / 2));  
    }  
      
    /** 
     * 设置窗口位于屏幕中间 
     * @param display 设备 
     * @param shell 要调整位置的窗口对象 
     */  
    public static void setCenter(Display display, Shell shell)  
    {  
        Rectangle bounds = display.getPrimaryMonitor().getBounds();  
        Rectangle rect = shell.getBounds();  
        int x = bounds.x + (bounds.width - rect.width) / 2;  
        int y = bounds.y + (bounds.height - rect.height) / 2;  
        shell.setLocation(x, y);  
    }  
      
}
