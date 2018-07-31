package com.iktpreobuka.true_project.entities.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class ClassRegisterDTO {
	@NotNull(message = "Year must be provided")
	@Min(value=9,message="Minimum value for year is 9.")
	@Max(value=16,message="Maximum value for year is 16.")
    private Integer year;
	
	@NotNull(message = "Class number must be provided")
	@Min(value=1,message="Minimum value for classNum is 1.")
	@Max(value=4,message="Maximum value for classNum is 4.")
	private Integer classNum;
	
	public ClassRegisterDTO() {}

	public ClassRegisterDTO(
			@NotNull(message = "Year must be provided") @Min(value=9,message="Minimum value for yearI is 9.")
			@Max(value=16,message="Maximum value for year is 16.") Integer year,
			@NotNull(message = "Class number must be provided") @Min(value=1,message="Minimum value for classNum is 1.")
			@Max(value=4,message="Maximum value for classNum is 4.") Integer classNum) {
		this.year=year;
		this.classNum=classNum;
	}
	
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getClassNum() {
		return classNum;
	}

	public void setClassNum(Integer classNum) {
		this.classNum = classNum;
	}

}
