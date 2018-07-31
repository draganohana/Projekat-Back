package com.iktpreobuka.true_project.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.iktpreobuka.true_project.entities.UserEntity;
import com.iktpreobuka.true_project.repositories.UserRepository;

public interface UserDaoImpl {
	
	

	UserEntity changePassword(UserEntity admin);

	Integer returnLoggedId(String email);
	
	
	
}
