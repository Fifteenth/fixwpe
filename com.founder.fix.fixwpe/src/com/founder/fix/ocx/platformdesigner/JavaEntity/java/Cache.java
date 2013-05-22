/**
 * Copyright c FOUNDER CORPORATION 2010 All Rights Reserved.
 * Cache.java
 */
package com.founder.fix.ocx.platformdesigner.JavaEntity.java;

import java.io.Serializable;

/**
 * [类名]<br>
 * Cache.java<br>
 * <br>
 * [功能概要]<br>
 * <br>
 * 缓存DTO
 * <br>
 * [变更履历]<br>
 * 2010-12-15 ver1.0 新建 wangzhiwei<br>
 * <br>
 *
 * @author wangzhiwei
 *
 */
public class Cache implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8162904075933925762L;

	/**
	 * 构造器
	 */
	public Cache() {
		super();
	}

	private String key;//缓存ID   
	private Object value;//缓存数据   
	private long timeOut;//更新时间   
	private boolean expired; //是否终止   

	public Cache(String key, Object value, long timeOut, boolean expired) {
		this.key = key;
		this.value = value;
		this.timeOut = timeOut;
		this.expired = expired;
	}

	public String getKey() {
		return key;
	}

	public long getTimeOut() {
		return timeOut;
	}

	public Object getValue() {
		return value;
	}

	public void setKey(String string) {
		key = string;
	}

	public void setTimeOut(long l) {
		timeOut = l;
	}

	public void setValue(Object object) {
		value = object;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean b) {
		expired = b;
	}
}

