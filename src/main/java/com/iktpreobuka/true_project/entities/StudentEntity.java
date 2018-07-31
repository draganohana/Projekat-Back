package com.iktpreobuka.true_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StudentEntity extends UserEntity {
	
	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH })
	@JsonIgnore
	private List<GradeEntity> grades = new ArrayList<>();
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "odeljenje")
	private ClassEntity odeljenje;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent")
	private ParentEntity parent;
	
	public StudentEntity(){
		super();
	}
	

	public List<GradeEntity> getGrades() {
		return grades;
	}


	public void setGrades(List<GradeEntity> grades) {
		this.grades = grades;
	}


	public ParentEntity getParent() {
		return parent;
	}

	public void setParent(ParentEntity parent) {
		this.parent = parent;
	}

	

	public ClassEntity getOdeljenje() {
		return odeljenje;
	}

	public void setOdeljenje(ClassEntity odeljenje) {
		this.odeljenje = odeljenje;
	}

	
}
