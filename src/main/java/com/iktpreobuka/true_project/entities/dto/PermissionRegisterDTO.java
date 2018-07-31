package com.iktpreobuka.true_project.entities.dto;

public class PermissionRegisterDTO {

	private Integer teacher;
	private Integer subject;
	
	public PermissionRegisterDTO() {
		super();
	}

	public PermissionRegisterDTO(Integer teacher, Integer subject) {
		super();
		this.teacher = teacher;
		this.subject = subject;
	}

	public Integer getTeacher() {
		return teacher;
	}

	public void setTeacher(Integer teacher) {
		this.teacher = teacher;
	}

	public Integer getSubject() {
		return subject;
	}

	public void setSubject(Integer subject) {
		this.subject = subject;
	}
	
	
}
