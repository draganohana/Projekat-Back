package com.iktpreobuka.true_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.true_project.entities.LoginInfo;
import com.iktpreobuka.true_project.repositories.UserRepository;

@Service
public class LoginDAO {

	@Autowired 
	private UserRepository userRepository;
	
	// Metoda za vracanje ID ulogovanog korisnika
	public Integer getId () {
		String email = LoginInfo.email;
		return userRepository.findByEmail(email).getId();
	}
	
}
