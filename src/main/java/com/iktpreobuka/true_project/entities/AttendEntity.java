package com.iktpreobuka.true_project.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AttendEntity {
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "cls")
	private ClassEntity cls;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "permission")
	private PermissionEntity permission;
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Integer attendId;
	
	@Version
	private Integer version;
	
	public AttendEntity() {
		super();
	}

	public ClassEntity getCls() {
		return cls;
	}

	public void setCls(ClassEntity cls) {
		this.cls = cls;
	}

	public PermissionEntity getPermission() {
		return permission;
	}

	public void setPermission(PermissionEntity permission) {
		this.permission = permission;
	}

	public Integer getAttendId() {
		return attendId;
	}

	public void setAttendId(Integer attendId) {
		this.attendId = attendId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
