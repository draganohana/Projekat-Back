package com.iktpreobuka.true_project.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.true_project.entities.PermissionEntity;


public interface PermissionRepository extends CrudRepository<PermissionEntity, Integer> {
	
	List<PermissionEntity> findByTeacherId(Integer teacher);
}
