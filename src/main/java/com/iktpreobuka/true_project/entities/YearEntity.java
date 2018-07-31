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
public class YearEntity {
	
	@OneToMany(mappedBy = "year", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH })
	@JsonIgnore
	private List<ClassEntity> classes = new ArrayList<>();
	
	@OneToMany(mappedBy = "year", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH })
	@JsonIgnore
	private List<CurriculumEntity> curriculums = new ArrayList<>();
	
	protected String yearName;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Integer yearId;
	
	@Version
	protected Integer version;
	
	
	public YearEntity() {
		super();
	}


	public String getYearName() {
		return yearName;
	}


	public void setYearName(String yearName) {
		this.yearName = yearName;
	}


	public Integer getYearId() {
		return yearId;
	}


	public void setYearId(Integer yearId) {
		this.yearId = yearId;
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


	public List<ClassEntity> getClasses() {
		return classes;
	}


	public void setClasses(List<ClassEntity> classes) {
		this.classes = classes;
	}

	
}
