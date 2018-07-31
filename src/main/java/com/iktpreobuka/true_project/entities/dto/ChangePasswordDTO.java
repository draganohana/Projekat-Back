package com.iktpreobuka.true_project.entities.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChangePasswordDTO {
	
	@NotNull(message = "New password must be provided.")
	@Size(min=3, max=10, message = "New password must be between {min} and {max} characters long.")
	private String newPassword;
	
	@NotNull(message = "Old password must be provided.")
	@Size(min=3, max=10, message = "Old password must be between {min} and {max} characters long.")
	private String oldPassword;
	
	@NotNull(message = "Repeated new password must be provided.")
	@Size(min=3, max=10, message = "Repeated new password must be between {min} and {max} characters long.")
	private String repeatNewPassword;
	
	public ChangePasswordDTO() {}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getRepeatNewPassword() {
		return repeatNewPassword;
	}

	public void setRepeatNewPassword(String repeatNewPassword) {
		this.repeatNewPassword = repeatNewPassword;
	}

	

	
}
