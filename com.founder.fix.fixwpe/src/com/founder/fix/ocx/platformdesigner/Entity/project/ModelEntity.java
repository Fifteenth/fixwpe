/**
 * Copyright c FOUNDER CORPORATION 2011 All Rights Reserved.
 * ModelEntity.java
 */
package com.founder.fix.ocx.platformdesigner.Entity.project;

import java.util.List;

/**
 * [类名]<br>
 * ModelEntity.java<br>
 * <br>
 * [功能概要]<br>
 *
 * <br>
 * [变更履历]<br>
 *
 * <br>
 * 2011-7-27 ver1.0 <br>
 * <br>
 *
 * @作者 wangzhiwei
 *
 */

public class ModelEntity {

	/**
	 * 构造器
	 */
	public ModelEntity() {
		// TODO Auto-generated constructor stub
	}

	private String name;
	
	private String transName;
	
	private String realPath;
	
	private String logicPath;
	
	private String webPath;
	
	private List<LeafEntity> leafs;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTransName() {
		return transName;
	}

	public void setTransName(String transName) {
		this.transName = transName;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public String getLogicPath() {
		return logicPath;
	}

	public void setLogicPath(String logicPath) {
		this.logicPath = logicPath;
	}

	public String getWebPath() {
		return webPath;
	}

	public void setWebPath(String webPath) {
		this.webPath = webPath;
	}

	public List<LeafEntity> getLeafs() {
		return leafs;
	}

	public void setLeafs(List<LeafEntity> leafs) {
		this.leafs = leafs;
	}
	
}
