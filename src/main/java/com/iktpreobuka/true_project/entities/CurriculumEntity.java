package com.iktpreobuka.true_project.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class CurriculumEntity {
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "year")
	private YearEntity year;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "subject")
	private SubjectEntity subject;
	
	private Integer weekHours;
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Integer currId;
	
	@Version
	private Integer version;
	
	public CurriculumEntity() {
		super();
	}

	public YearEntity getYear() {
		return year;
	}

	public void setYear(YearEntity year) {
		this.year = year;
	}

	public SubjectEntity getSubject() {
		return subject;
	}

	public void setSubject(SubjectEntity subject) {
		this.subject = subject;
	}

	public Integer getWeekHours() {
		return weekHours;
	}

	public void setWeekHours(Integer weekHours) {
		this.weekHours = weekHours;
	}

	public Integer getCurrId() {
		return currId;
	}

	public void setCurrId(Integer currId) {
		this.currId = currId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
}
