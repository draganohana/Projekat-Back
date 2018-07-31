package com.iktpreobuka.true_project.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GradeEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Integer gradeId;
	
	private Integer gradeValue;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "student")
    private StudentEntity student;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "attend")
	private AttendEntity attend;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "gradeType")
	private GradeTypeEntity gradeType;

	@JsonFormat(shape = JsonFormat.Shape.STRING,
	pattern = "dd-MM-yyyy hh:mm:ss")
	private Date date;
	
	@Version
	private Integer version;
	
	public GradeEntity () {
		super();
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public Integer getGradeValue() {
		return gradeValue;
	}

	public void setGradeValue(Integer gradeValue) {
		this.gradeValue = gradeValue;
	}

	public StudentEntity getStudent() {
		return student;
	}

	public void setStudent(StudentEntity student) {
		this.student = student;
	}

	public AttendEntity getAttend() {
		return attend;
	}

	public void setAttend(AttendEntity attend) {
		this.attend = attend;
	}

	public GradeTypeEntity getGradeType() {
		return gradeType;
	}

	public void setGradeType(GradeTypeEntity gradeType) {
		this.gradeType = gradeType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	
	
}
