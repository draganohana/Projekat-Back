package com.iktpreobuka.true_project.entities.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SubjectRegisterDTO {
    
	@NotNull(message = "Subject name must be provided.")
	@Size(min = 4, max = 30, message = "Subject name must be between {min} and {max} character long.")
	private String subjName;
	
	
	public SubjectRegisterDTO() {
		super();
	}
	public SubjectRegisterDTO(
			@NotNull(message = "Subject name must be provided.") 
			@Size(min = 4, max = 30, message = "Subject name must be between {min} and {max} character long.") 
			String subjName

			)
	{
		this.subjName = subjName;
	}

	public String getSubjName() {
		return subjName;
	}

	public void setSubjName(String subjName) {
		this.subjName = subjName;
	}

}
