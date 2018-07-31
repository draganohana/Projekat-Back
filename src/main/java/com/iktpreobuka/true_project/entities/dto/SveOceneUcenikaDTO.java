package com.iktpreobuka.true_project.entities.dto;

import java.util.ArrayList;
import java.util.List;

import com.iktpreobuka.true_project.entities.GradeEntity;
import com.iktpreobuka.true_project.entities.SubjectEntity;

public class SveOceneUcenikaDTO {
	
protected SubjectEntity subject;
protected List<GradeEntity> grades = new ArrayList<>();

public SveOceneUcenikaDTO() {
	super();
}

public SveOceneUcenikaDTO(SubjectEntity subject, List<GradeEntity> grades) {
	super();
	this.subject = subject;
	this.grades = grades;
}

public SubjectEntity getSubject() {
	return subject;
}

public void setSubject(SubjectEntity subject) {
	this.subject = subject;
}

public List<GradeEntity> getGrades() {
	return grades;
}

public void setGrades(List<GradeEntity> grades) {
	this.grades = grades;
}

}


