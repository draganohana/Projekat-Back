package com.iktpreobuka.true_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClassEntity {
	
	@Id
	@JsonIgnore
	@GeneratedValue(strategy=GenerationType.TABLE )
	private Integer classId;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "year")
	private YearEntity year;
	
	private Integer classNum;
	
	@OneToMany(mappedBy = "odeljenje", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH })
	@JsonIgnore
	private List<StudentEntity> students = new ArrayList<>();
	
	@OneToMany(mappedBy = "cls", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH })
	@JsonIgnore
	private List<AttendEntity> attends = new ArrayList<>();
	
	@Version
	private Integer version;
	
	public ClassEntity() {
		super();
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getClassNum() {
		return classNum;
	}

	public void setClassNum(Integer classNum) {
		this.classNum = classNum;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	public List<StudentEntity> getStudents() {
		return students;
	}

	public void setStudents(List<StudentEntity> students) {
		this.students = students;
	}

	public List<AttendEntity> getAttends() {
		return attends;
	}

	public void setAttends(List<AttendEntity> attends) {
		this.attends = attends;
	}

	public YearEntity getYear() {
		return year;
	}

	public void setYear(YearEntity year) {
		this.year = year;
	}


}
