/**
 * Copyright c FOUNDER CORPORATION 2011 All Rights Reserved.
 * TreeViewerLabelProvider.java
 */
package com.founder.fix.ocx.platformdesigner.trees;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.founder.fix.base.wpe.FixImageProvider;
import com.founder.fix.ocx.util.FixUtil;


/**
 * [类名]<br>
 * TreeViewerLabelProvider.java<br>
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
public class TreeViewerLabelProvider implements ILabelProvider {

	/**
	 * 
	 */
	public TreeViewerLabelProvider() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 */
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		// TODO Auto-generated method stub
		// 系统自定义图标
		String img1 = ISharedImages.IMG_TOOL_PASTE;
		String img2 = ISharedImages.IMG_OBJ_FOLDER;
		String img3 = ISharedImages.IMG_OBJ_FILE;
		String img4 = ISharedImages.IMG_ETOOL_DEF_PERSPECTIVE;
		String img5 = ISharedImages.IMG_OBJ_PROJECT;
		
//		String img3 = ISharedImages.IMG_DEF_VIEW;
		
		ITreeElement tElement = (ITreeElement) element;
		int icon = tElement.getIcon();
		if(icon == EntityElement.PROJECT) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(img1);
		} else if(icon == EntityElement.FOLDER) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(img2);
		} else if(icon == EntityElement.FILE) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(img3);
		} else if(icon == EntityElement.VIEW) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(img4);
		} else if(icon == EntityElement.PROJECT_ECLIPSE) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(img5);
		} else if(icon == EntityElement.DATASOURCE) {
			return FixUtil.getImageFromURL(FixImageProvider.DATASOURCE);
		} else if(icon == EntityElement.BPMN) {
			return FixUtil.getImageFromURL(FixImageProvider.BPMN_ICON);
		} else if(icon == EntityElement.JAVA) {
			return FixUtil.getImageFromURL(FixImageProvider.JAVA);
		} else if(icon == EntityElement.HTML) {
			return FixUtil.getImageFromURL(FixImageProvider.HTML);
		} else if(icon == EntityElement.XML) {
			return FixUtil.getImageFromURL(FixImageProvider.XML);
		} else if(icon == EntityElement.VIW) {
			return FixUtil.getImageFromURL(FixImageProvider.VIW);
		} else if(icon == EntityElement.SEV) {
			return FixUtil.getImageFromURL(FixImageProvider.SEV);
		} else if(icon == EntityElement.BIZ) {
			return FixUtil.getImageFromURL(FixImageProvider.BIZ);
		} else if(icon == EntityElement.SEV_METHOD) {
			return FixUtil.getImageFromURL(FixImageProvider.SEV_METHOD);
		} else if(icon == EntityElement.LINK_SOURCE) {
			return FixUtil.getImageFromURL(FixImageProvider.LINK_SOURCE);
		} else if(icon == EntityElement.MODULE) {
			return FixUtil.getImageFromURL(FixImageProvider.MODULE);
		} else if(icon == EntityElement.SEV_PACKAGE) {
			return FixUtil.getImageFromURL(FixImageProvider.SEV_PACKAGE);
		} else if(icon == EntityElement.BIZ_PACKAGE) {
			return FixUtil.getImageFromURL(FixImageProvider.BIZ_PACKAGE);
		} else if(icon == EntityElement.BPMN_PACKAGE) {
			return FixUtil.getImageFromURL(FixImageProvider.BPMN_PACKAGE);
		} else if(icon == EntityElement.CONFIG_PACKAGE) {
			return FixUtil.getImageFromURL(FixImageProvider.CONFIG_PACKAGE);
		} else if(icon == EntityElement.FORM_VIEW) {
			return FixUtil.getImageFromURL(FixImageProvider.FORM_VIEW);
		} else if(icon == EntityElement.OTHER) {
			return FixUtil.getImageFromURL(FixImageProvider.OTHER);
		} else if(icon == EntityElement.SPRING) {
			return FixUtil.getImageFromURL(FixImageProvider.SPRING);
		} else if(icon == EntityElement.PROJECT_PACKAGE) {
			return FixUtil.getImageFromURL(FixImageProvider.PROJECT_PACKAGE);
		} else if(icon == EntityElement.JSP) {
			return FixUtil.getImageFromURL(FixImageProvider.JSP);
		} else if(icon == EntityElement.JS) {
			return FixUtil.getImageFromURL(FixImageProvider.JS);
		} else if(icon == EntityElement.IMG) {
			return FixUtil.getImageFromURL(FixImageProvider.IMG);
		} else if(icon == EntityElement.CSS) {
			return FixUtil.getImageFromURL(FixImageProvider.CSS);
		} else if(icon == EntityElement.JAVA_PROJECT) {
			return FixUtil.getImageFromURL(FixImageProvider.JAVA_PROJECT);
		} else if(icon == EntityElement.WEB_PROJECT) {
			return FixUtil.getImageFromURL(FixImageProvider.WEB_PROJECT);
		} else if(icon == EntityElement.MENU) {
			return FixUtil.getImageFromURL(FixImageProvider.MENU);
		} else if(icon == EntityElement.OBJFORM) {
			return FixUtil.getImageFromURL(FixImageProvider.OBJFORM);
		} else if(icon == EntityElement.DICTIONARYTABLE) {
			return FixUtil.getImageFromURL(FixImageProvider.DICTIONARYTABLE);
		} else if(icon == EntityElement.DATASOURCE_LEAF) {
			return FixUtil.getImageFromURL(FixImageProvider.DATASOURCE_LEAF);
		} else if(icon == EntityElement.EVENT) {
			return FixUtil.getImageFromURL(FixImageProvider.EVENT);
		} else if(icon == EntityElement.UNIFORM) {
			return FixUtil.getImageFromURL(FixImageProvider.UNIFORM);
		} else if(icon == EntityElement.FORM) {
			return FixUtil.getImageFromURL(FixImageProvider.FORM);
		} else if(icon == EntityElement.COMPONENTS) {
			return FixUtil.getImageFromURL(FixImageProvider.COMPONENTS);
		} else if(icon == EntityElement.BIZ_COMPONENTS) {
			return FixUtil.getImageFromURL(FixImageProvider.BIZ_COMPONENTS);
		} else if(icon == EntityElement.FUNC_COMPONENTS) {
			return FixUtil.getImageFromURL(FixImageProvider.FUNC_COMPONENTS);
		} else if(icon == EntityElement.SEV_COMPONENTS) {
			return FixUtil.getImageFromURL(FixImageProvider.SEV_COMPONENTS);
		} else if(icon == EntityElement.FORM_COMPONENTS) {
			return FixUtil.getImageFromURL(FixImageProvider.FORM_COMPONENTS);
		} else if(icon == EntityElement.JS_COMPONENT) {
			return FixUtil.getImageFromURL(FixImageProvider.JS_COMPONENT);
		} else if(icon == EntityElement.SERVICE_COMPONENT) {
			return FixUtil.getImageFromURL(FixImageProvider.SERVICE_COMPONENT);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		// TODO Auto-generated method stub
		ITreeElement tElement = (ITreeElement) element;
		return tElement.getName();
	}

}
