package com.iktpreobuka.true_project.entities.dto;


public class AttendRegisterDTO {

	private Integer cls;
	private Integer permission;
	
	public AttendRegisterDTO() {
		super ();
	}

	public AttendRegisterDTO(Integer cls, Integer permission) {
		super();
		this.cls = cls;
		this.permission = permission;
	}

	public Integer getCls() {
		return cls;
	}

	public void setCls(Integer cls) {
		this.cls = cls;
	}

	public Integer getPermission() {
		return permission;
	}

	public void setPermission(Integer permission) {
		this.permission = permission;
	}
	
	
}
