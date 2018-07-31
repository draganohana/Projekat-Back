package com.iktpreobuka.true_project.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.true_project.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

	UserEntity findByUsername(String username);
	
	UserEntity findByEmail(String email);
	

}
