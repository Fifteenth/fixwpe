/**
 * Copyright c FOUNDER CORPORATION 2011 All Rights Reserved.
 * EntityElement.java
 */
package com.founder.fix.ocx.platformdesigner.trees;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IEditorInput;

/**
 * [类名]<br>
 * EntityElement.java<br>
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
public class EntityElement implements ITreeElement {
	
	//树节点图标
	public static final int PROJECT = 1;
	public static final int FOLDER = 2;
	public static final int FILE = 3;
	public static final int VIEW = 4;
	public static final int DATASOURCE = 5;
	public static final int BPMN = 6;
	public static final int JAVA = 7;
	public static final int HTML = 8;
	public static final int XML = 9;
	public static final int VIW = 10;
	public static final int SEV = 11;
	public static final int BIZ = 12;
	public static final int SEV_METHOD = 13;
	public static final int LINK_SOURCE = 14;
	public static final int MODULE = 15;
	public static final int SEV_PACKAGE = 16;
	public static final int BIZ_PACKAGE = 17;
	public static final int BPMN_PACKAGE = 18;
	public static final int CONFIG_PACKAGE = 19;
	public static final int FORM_VIEW = 20;
	public static final int OTHER = 21;
	public static final int SPRING = 22;
	public static final int PROJECT_PACKAGE = 23;
	public static final int CSS = 24;
	public static final int JS = 25;
	public static final int JSP = 26;
	public static final int IMG = 27;
	public static final int JAVA_PROJECT = 28;
	public static final int WEB_PROJECT = 29;
	public static final int MENU = 30;
	public static final int OBJFORM = 31;
	public static final int DICTIONARYTABLE = 32;
	public static final int DATASOURCE_LEAF = 33;
	public static final int EVENT = 34;
	public static final int UNIFORM = 35;
	public static final int FORM = 36;
	public static final int PROJECT_ECLIPSE = 37;
	public static final int COMPONENTS = 38;
	public static final int BIZ_COMPONENTS = 39;
	public static final int FUNC_COMPONENTS = 40;
	public static final int SEV_COMPONENTS = 41;
	public static final int FORM_COMPONENTS = 42;
	public static final int JS_COMPONENT = 43;
	public static final int SERVICE_COMPONENT = 44;
	
	//节点对象
	private Object object;
	
	//父节点
	private ITreeElement parentElement;
	
	//节点的id标示，表明这个节点的类型、一般为字符串常量
	private String id;

	//需要打开的编辑器
	private IEditorInput editorInput;
	
	//节点名称、即该节点显示的名称
	private String name;
	
	//与上面的name相对应，节点对应的真实文件的名称
	private String realName;
	
	//节点对应的真实目录文件的路径
	private String realPath;
	
	//节点所用图片的标识
	private int icon;
	
	//根节点包含的集合，封装在list中、标示子节点
	private List<ITreeElement> list = new ArrayList<ITreeElement>();
	
	
	private String tooltip;
	
	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	/**
	 * 构造器
	 */
	public EntityElement() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 构造器
	 */
	public EntityElement(String id, String name, int icon) {
		this.name = name;
		this.id = id;
		this.icon = icon;
	}

	/**
	 * 构造器
	 */
	public EntityElement(String id, String name, int icon,String tooltip) {
		this.name = name;
		this.id = id;
		this.icon = icon;
		this.tooltip = tooltip;
	}
	/**
	 * 构造器
	 */
	public EntityElement(String name, int icon) {
		this.name = name;
		this.icon = icon;
	}
	
	/**
	 * 构造器
	 */
	public EntityElement(ITreeElement parentElement, String id, String name, int icon, String realPath) {
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.realPath = realPath;
		this.parentElement = parentElement;
	}
	
	/**
	 * 构造器
	 */
	public EntityElement(ITreeElement parentElement, String id, String name, String realName, int icon, String realPath) {
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.realPath = realPath;
		this.parentElement = parentElement;
		this.realName = realName;
	}

	/* (non-Javadoc)
	 * @see prespectve.tree.ITreeElement#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see prespectve.tree.ITreeElement#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see prespectve.tree.ITreeElement#setChildren(java.util.List)
	 */
	public void setChildren(List<ITreeElement> children) {
		this.list = children;
	}

	/* (non-Javadoc)
	 * @see prespectve.tree.ITreeElement#getChildren()
	 */
	public List<ITreeElement> getChildren() {
		return list;
	}

	/* (non-Javadoc)
	 * @see prespectve.tree.ITreeElement#hasChildren()
	 */
	public boolean hasChildren() {
		if(list.size() > 0) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see prespectve.tree.ITreeElement#addChild(prespectve.tree.ITreeElement)
	 */
	public void addChild(ITreeElement treeElement) {
		list.add(treeElement);
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public ITreeElement getParent() {
		return this.parentElement;
	}

	public void removeChild(ITreeElement treeElement) {
		list.remove(treeElement);
	}
	
	public IEditorInput getEditorInput() {
		return editorInput;
	}

	public void setEditorInput(IEditorInput editorInput) {
		this.editorInput = editorInput;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Object getData() {
		return object;
	}

	public void setData(Object object) {
		this.object = object;
	}

	public ITreeElement getParentElement() {
		return parentElement;
	}

	public void setParent(ITreeElement parentElement) {
		this.parentElement = parentElement;
	}
	
}
