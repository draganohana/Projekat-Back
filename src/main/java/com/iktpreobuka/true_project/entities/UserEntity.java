package com.iktpreobuka.true_project.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.true_project.enumerations.EUserRole;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Inheritance (strategy=InheritanceType.JOINED)
public abstract class UserEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Integer id;
	
	private String firstName;

	private String lastName;
	
	private String username;

	private String email;
	
	@JsonIgnore
	private String  password;
	
	@Enumerated(EnumType.STRING)
	EUserRole role;
	
	@Version
	private Integer version;
	
	public UserEntity() {
	super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
	public EUserRole getRole() {
		return role;
	}
	
	public void setRole(EUserRole role) {
		this.role = role;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
}
