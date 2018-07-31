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
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PermissionEntity {

	

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher")
	@JsonManagedReference
	private TeacherEntity teacher;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "subject")
	@JsonManagedReference
	private SubjectEntity subject;
	
	 @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH })
	@JsonIgnore
	private List<AttendEntity> attends = new ArrayList<>();
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Integer permId;
	
	@Version
	private Integer version;
	
	public PermissionEntity() {
		super();
	}
	

	public PermissionEntity(TeacherEntity teacher, SubjectEntity subject, Integer permId, Integer version) {
		super();
		this.teacher = teacher;
		this.subject = subject;
		this.permId = permId;
		this.version = version;
	}


	public Integer getPermId() {
		return permId;
	}

	public void setPermId(Integer permId) {
		this.permId = permId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public TeacherEntity getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherEntity teacher) {
		this.teacher = teacher;
	}

	public void setSubject(SubjectEntity subject) {
		this.subject = subject;
	}
	public SubjectEntity getSubject() {
		return subject;
	}

}
