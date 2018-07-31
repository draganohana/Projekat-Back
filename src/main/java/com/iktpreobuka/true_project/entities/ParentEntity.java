package com.iktpreobuka.true_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ParentEntity extends UserEntity {
	
	@JsonIgnore
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH })
	private List<StudentEntity> students = new ArrayList<>();

	public ParentEntity(){
		super();
	}
	
	public List<StudentEntity> getStudents() {
		return students;
	}

	public void setStudents(List<StudentEntity> students) {
		this.students = students;
	}
	

}	
	

