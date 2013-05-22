/**
 * 
 */
package com.founder.fix.ocx.platformdesigner.Entity.project;

/**
 * @author wangzhiwei
 *
 */
public class BaseEntity {

	/**
	 * 
	 */
	public BaseEntity() {
		// TODO Auto-generated constructor stub
	}

	private String id;
	
	private String name;
	
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
