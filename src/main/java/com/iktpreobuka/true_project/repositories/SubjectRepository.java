package com.iktpreobuka.true_project.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.true_project.entities.SubjectEntity;

public interface SubjectRepository extends CrudRepository<SubjectEntity, Integer> {

	SubjectEntity findBySubjName(String subjName);
	

}
