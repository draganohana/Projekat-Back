package com.iktpreobuka.true_project.entities.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class YearRegisterDTO {

	@NotNull(message = "Year must be provided.")
	@Size(min=4, max=7, message = "Year must be between {min} and {max} characters long.")
	protected String yearName;
	
	public YearRegisterDTO() {}
	
	public YearRegisterDTO(
			@NotNull(message = "Year must be provided.")
			@Size(min=4, max=7, message = "Year must be between {min} and {max} characters long.") String yearName
			) {
		super();
		this.yearName=yearName;
	}

	public String getYearName() {
		return yearName;
	}

	public void setYearName(String yearName) {
		this.yearName = yearName;
	}
}
