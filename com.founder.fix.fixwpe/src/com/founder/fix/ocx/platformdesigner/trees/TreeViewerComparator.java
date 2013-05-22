/**
 * Copyright c FOUNDER CORPORATION 2011 All Rights Reserved.
 * TreeViewerComparator.java
 */
package com.founder.fix.ocx.platformdesigner.trees;

import java.io.File;
import java.text.Collator;
import java.util.Comparator;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

import com.founder.fix.ocx.util.FixUtil;


/**
 * [类名]<br>
 * TreeViewerComparator.java<br>
 * <br>
 * [功能概要]<br>
 *
 * <br>
 * [变更履历]<br>
 *
 * <br>
 * 2011-8-18 ver1.0 <br>
 * <br>
 *
 * @作者 wangzhiwei
 *
 */

public class TreeViewerComparator extends ViewerComparator {

	/**
	 * 构造器
	 */
	public TreeViewerComparator() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 构造器
	 */
	public TreeViewerComparator(Comparator comparator) {
		super(comparator);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		EntityElement element1 = (EntityElement) e1;
		EntityElement element2 = (EntityElement) e2;

		// 查看节点是否是同一类型
		if (new File(element1.getRealPath()).isDirectory()
				&& new File(element2.getRealPath()).isDirectory()) {
			return FixUtil.compareChineseChars(dealwithName(element1.getName()), 
					dealwithName(element2.getName()));
		} else if (new File(element1.getRealPath()).isDirectory()
				&& !new File(element2.getRealPath()).isDirectory()) {
			return -1;
		} else if (!new File(element1.getRealPath()).isDirectory()
				&& new File(element2.getRealPath()).isDirectory()) {
			return 1;
		} else if (!new File(element1.getRealPath()).isDirectory()
				&& !new File(element2.getRealPath()).isDirectory()) {
			// 中文拼音排序
			return FixUtil.compareChineseChars(dealwithName(element1.getName()), 
					dealwithName(element2.getName()));
		} else {
			return 0;
		}
	}
	
	/**
	 * 处理名称
	 */
	private String dealwithName(String name) {
		if(name != null && name.lastIndexOf(".") != -1) {
			return name.substring(0, name.lastIndexOf("."));
		}
		return name;
	}
	
}
