package com.iktpreobuka.true_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TeacherEntity extends UserEntity{
	
	@OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH })
	//@JsonIgnore
	@JsonBackReference
	private List<PermissionEntity> permissions = new ArrayList<>();
	
    
	public TeacherEntity() {
		super();
	}
	
	public List<PermissionEntity> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionEntity> permissions) {
		this.permissions = permissions;
	}

}