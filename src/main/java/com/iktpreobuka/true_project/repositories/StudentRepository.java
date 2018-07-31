package com.iktpreobuka.true_project.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.true_project.entities.ClassEntity;
import com.iktpreobuka.true_project.entities.StudentEntity;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer> {

  List<StudentEntity> findByOdeljenje(ClassEntity cls);

  StudentEntity findByParentId(Integer id);





	

	
}
