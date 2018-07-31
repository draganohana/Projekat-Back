package com.iktpreobuka.true_project.entities.dto;

public class EditGradeDTO {
	private Integer gradeValue;
	private Integer gradeType;
	
	public EditGradeDTO() {
		super();
	}

	public Integer getGradeValue() {
		return gradeValue;
	}

	public void setGradeValue(Integer gradeValue) {
		this.gradeValue = gradeValue;
	}

	public Integer getGradeType() {
		return gradeType;
	}

	public void setGradeType(Integer gradeType) {
		this.gradeType = gradeType;
	}
}
