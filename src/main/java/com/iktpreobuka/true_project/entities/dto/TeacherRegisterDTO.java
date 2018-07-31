package com.iktpreobuka.true_project.entities.dto;



import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



public class TeacherRegisterDTO {

	@NotNull(message = "First name must be provided.")
	@Size(min=2, max=30, message = "First name must be between {min} and {max} characters long.")
	private String firstName;
	
	@NotNull(message = "Last name must be provided.")
	@Size(min=2, max=30, message = "Last name must be between {min} and {max} characters long.")
	private String lastName;
	
	@NotNull(message = "Username must be provided.")
	@Size(min=2, max=30, message = "Username must be between {min} and {max} characters long.")
	private String username;
	
	@NotNull(message = "Email must be provided.")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message="Email is not valid.")
	private String email;
	
	@NotNull(message = "Password must be provided.")
	@Size(min=3, max=10, message = "Password must be between {min} and {max} characters long.")
	private String password;
	
	
	public TeacherRegisterDTO() {}
	
	public TeacherRegisterDTO(
			@NotNull(message = "First name must be provided.") @Size(min = 2, max = 30, message = "First name must be between {min} and {max} character long.") String firstName,
			@NotNull(message = "Last name must be provided.") @Size(min = 2, max = 30, message = "Last name must be between {min} and {max} character long.") String lastName,
			@NotNull(message = "Username must be provided.") @Size(min = 2, max = 15, message = "Username must be between {min} and {max} character long.") String username,
			@NotNull(message = "Email must be provided.") @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message="Email is not valid.") String email,
			@NotNull(message = "Password must be provided.") @Size(min = 3, max = 15, message = "Password must be between {min} and {max} character long.") String password)
			
	{
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		

	}
	
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
