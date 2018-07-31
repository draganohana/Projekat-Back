package com.iktpreobuka.true_project.entities.dto;

import javax.validation.constraints.NotNull;

public class GradeTypeRegisterDTO {
	
	@NotNull(message="Grade type must be provided.")
	private String type;

	public GradeTypeRegisterDTO() {}
	
	public GradeTypeRegisterDTO(
			@NotNull(message="Grade type must be provided.") String type
			) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
