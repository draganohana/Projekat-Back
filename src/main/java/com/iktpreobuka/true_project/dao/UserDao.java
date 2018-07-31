package com.iktpreobuka.true_project.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.true_project.entities.UserEntity;
import com.iktpreobuka.true_project.repositories.UserRepository;

@Service
public class UserDao implements UserDaoImpl{

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional
	public UserEntity changePassword(UserEntity admin) {
		return em.merge(admin);
	}
	
	@Override
	public Integer returnLoggedId(String email){
			return userRepository.findByEmail(email).getId();
	}
	
	

}
