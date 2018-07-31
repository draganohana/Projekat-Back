package com.iktpreobuka.true_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SubjectEntity {
	
	
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH })
	//@JsonIgnore
	@JsonBackReference
	private List<PermissionEntity> permissions = new ArrayList<>();
	

	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH })
	@JsonIgnore
	private List<CurriculumEntity> curriculums = new ArrayList<>();
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer subjId;
	
	private String subjName; 
	
	@Version
	private Integer version;
	
	public SubjectEntity() {
		super();
	}


	public List<PermissionEntity> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionEntity> permissions) {
		this.permissions = permissions;
	}

	public Integer getSubjId() {
		return subjId;
	}

	public void setSubjId(Integer subjId) {
		this.subjId = subjId;
	}

	public String getSubjName() {
		return subjName;
	}

	public void setSubjName(String subjName) {
		this.subjName = subjName;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}


	public List<CurriculumEntity> getCurriculums() {
		return curriculums;
	}


	public void setCurriculums(List<CurriculumEntity> curriculums) {
		this.curriculums = curriculums;
	}	

}
