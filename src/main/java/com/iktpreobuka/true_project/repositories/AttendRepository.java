package com.iktpreobuka.true_project.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.true_project.entities.AttendEntity;
import com.iktpreobuka.true_project.entities.ClassEntity;


public interface AttendRepository extends CrudRepository<AttendEntity, Integer> {

	List<AttendEntity> findByCls(ClassEntity cls);

	

}
