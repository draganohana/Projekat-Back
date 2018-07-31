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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GradeTypeEntity {
	
	@OneToMany(mappedBy = "gradeType", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH })
	@JsonIgnore
	private List<GradeEntity> grades = new ArrayList<>();
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Integer gTypeId;
	
	private String type;

	@Version
	private Integer version;
	
	public GradeTypeEntity() {
		super();
	}

	

	public Integer getgTypeId() {
		return gTypeId;
	}



	public void setgTypeId(Integer gTypeId) {
		this.gTypeId = gTypeId;
	}



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}



	public List<GradeEntity> getGrades() {
		return grades;
	}



	public void setGrades(List<GradeEntity> grades) {
		this.grades = grades;
	}
	
} 
