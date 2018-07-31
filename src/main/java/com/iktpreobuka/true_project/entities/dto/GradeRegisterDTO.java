package com.iktpreobuka.true_project.entities.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;




public class GradeRegisterDTO {
    
	@NotNull(message = "Grade value must be provided.")
	@Min(value=1,message="Minimum grade value is 1.")
	@Max(value=5,message="Maximum grade value is 5.")
	private Integer gradeValue;
	
	private Integer student;
	
	private Integer attend;
		
	private Integer gradeType;
	
	public GradeRegisterDTO() {
		super();
	}
	public GradeRegisterDTO(
			@NotNull(message = "Grade value must be provided.") @Min(value=1,message="Minimum value for grade value is 1.")
			@Max(value=5,message="Maximum value for grade value is 5.") Integer gradeValue,
			Integer student, Integer attend, Integer gradeType) {
		this.gradeValue = gradeValue;
		this.student = student;
		this.attend = attend;
		this.gradeType = gradeType;
	}
	public Integer getGradeValue() {
		return gradeValue;
	}
	public void setGradeValue(Integer gradeValue) {
		this.gradeValue = gradeValue;
	}
	public Integer getAttend() {
		return attend;
	}
	public void setAttend(Integer attend) {
		this.attend = attend;
	}
	public Integer getStudent() {
		return student;
	}
	public void setStudent(Integer student) {
		this.student = student;
	}
	public Integer getGradeType() {
		return gradeType;
	}
	public void setGradeType(Integer gradeType) {
		this.gradeType = gradeType;
	}

	
	
}
