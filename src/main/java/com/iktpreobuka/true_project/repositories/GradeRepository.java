package com.iktpreobuka.true_project.repositories;

import java.util.List;


import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.true_project.entities.GradeEntity;
import com.iktpreobuka.true_project.entities.StudentEntity;
import com.iktpreobuka.true_project.entities.SubjectEntity;

public interface GradeRepository extends CrudRepository<GradeEntity, Integer> {

	List <GradeEntity> findByStudent_idAndAttend_Permission_Subject(Integer student, SubjectEntity se);
	
	List <GradeEntity> findByStudent_id(Integer id);

	List<GradeEntity> findByStudentAndAttend_Permission_Subject(StudentEntity student, SubjectEntity subject);
	
	

}
