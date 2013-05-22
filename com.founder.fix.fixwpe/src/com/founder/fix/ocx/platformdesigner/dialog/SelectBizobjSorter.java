/**
 * @Title: BizobjSorter.java
 * @Package com.founder.fix.studio.platformdesigner.sortors
 * @Description: TODO
 * Copyright: Copyright (c) 2012 
 * Company:方正国际软件有限公司
 * 
 * @author Comsys-Administrator
 * @date 2012-6-26 上午10:58:41
 * @version v1.0
 */
package com.founder.fix.ocx.platformdesigner.dialog;

import java.text.Collator;
import java.util.Comparator;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.founder.fix.base.platformdesigner.Entity.project.BizobjEntity;
import com.founder.fix.ocx.util.FixUtil;


/**
 * @ClassName: BizobjSorter
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2012-6-26 上午10:58:41
 *
 */
public class SelectBizobjSorter extends ViewerSorter {
	
	private int sortType;
	private static final int ID = 1;
	 private static final int NAME = 2;
	
	public static final SelectBizobjSorter ID_ASC = new SelectBizobjSorter(ID);
    public static final SelectBizobjSorter ID_DESC = new SelectBizobjSorter(-ID);
    public static final SelectBizobjSorter NAME_ASC = new SelectBizobjSorter(NAME);
    public static final SelectBizobjSorter NAME_DESC = new SelectBizobjSorter(-NAME);

	/**
	 * 创建一个新的实例 BizobjSorter. 
	 * <p>Title: </p>
	 * <p>Description: </p>
	 */
	public SelectBizobjSorter(int sortType) {
		this.sortType = sortType;
	}

	/**
	 * 创建一个新的实例 BizobjSorter. 
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param collator
	 */
	public SelectBizobjSorter(Collator collator) {
		super(collator);
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		BizobjEntity entity1 = (BizobjEntity) e1;
		BizobjEntity entity2 = (BizobjEntity) e2;

		switch (sortType) {
			case ID: {
				return entity1.getId().compareTo(entity2.getId());
			}
			case -ID: {
				return entity2.getId().compareTo(entity1.getId());
			}
			case NAME: {
				// 中文拼音排序
				return FixUtil.compareChineseChars(entity1.getName(),
						entity2.getName());
			}
			case -NAME: {
				// 中文拼音排序
				return FixUtil.compareChineseChars(entity2.getName(),
						entity1.getName());
			}
		}

		return 0;
	}

}
