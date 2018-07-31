package com.iktpreobuka.true_project.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.true_project.controllers.util.RESTError;
import com.iktpreobuka.true_project.entities.CurriculumEntity;
import com.iktpreobuka.true_project.entities.SubjectEntity;
import com.iktpreobuka.true_project.entities.YearEntity;
import com.iktpreobuka.true_project.entities.dto.CurriculumRegisterDTO;
import com.iktpreobuka.true_project.repositories.CurriculumRepository;
import com.iktpreobuka.true_project.repositories.SubjectRepository;
import com.iktpreobuka.true_project.repositories.YearRepository;

@RestController
@RequestMapping(path = "/curriculum")
public class CurriculumController {
	
	@Autowired
	private CurriculumRepository curriculumRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private YearRepository yearRepository;
	
	// Metod za unos curriculum-a
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createCurriculum( @RequestBody CurriculumRegisterDTO curriculum) {
		SubjectEntity subject = subjectRepository.findById(curriculum.getSubject()).get();
		YearEntity year = yearRepository.findById(curriculum.getYear()).get();
		CurriculumEntity ce = new CurriculumEntity();
		ce.setSubject(subject);
		ce.setYear(year);
		curriculumRepository.save(ce);
		return new ResponseEntity<CurriculumEntity>(ce, HttpStatus.OK);
	}
	
	//Metod za izlistavanje svih curriculum-a
	 @Secured("ROLE_ADMIN")
		@RequestMapping(method = RequestMethod.GET)
		public ResponseEntity<?> getAllCurriculums() {
			return new ResponseEntity<>(curriculumRepository.findAll(), HttpStatus.OK);
		}
	 // Metod za brisanje pojedinih curriculum-a
	 @Secured("ROLE_ADMIN")
	 @RequestMapping(method = RequestMethod.DELETE, value = "/{curriculumId}")
	 public ResponseEntity<?> deleteCurriculum(@PathVariable String curriculumId) {
		 try {
				Integer id = Integer.parseInt(curriculumId);
				CurriculumEntity ce = curriculumRepository.findById(id).get();
				if (ce == null) {
					return new ResponseEntity<RESTError>(new RESTError(1,"Curriculum with provided ID not found."),
							HttpStatus.NOT_FOUND);
				}
				curriculumRepository.deleteById(id);
				return new ResponseEntity<CurriculumEntity>(ce, HttpStatus.OK);
	 }
		 catch (Exception e) {
			 return new ResponseEntity<>(new RESTError(1, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
	 }
	 // Metod za izlistavanje curriculum-a po Id-ju
	 @Secured("ROLE_ADMIN")
		@RequestMapping(method = RequestMethod.GET, value = "/{curriculumId}")
		public ResponseEntity<?> getAttendById(@PathVariable String curriculumId) {
			Integer id = Integer.parseInt(curriculumId);
			CurriculumEntity ce = curriculumRepository.findById(id).get();
			return new ResponseEntity<CurriculumEntity>(ce, HttpStatus.OK);
		}
	// Metod za izmenu pojedinih curriculum-a
		 @Secured("ROLE_ADMIN")
			@RequestMapping(method = RequestMethod.PUT, value = "/{curriculumId}")
			public ResponseEntity<?> updateCurriculum(@PathVariable String curriculumId,
					@Valid @RequestBody CurriculumRegisterDTO updateCurriculum) {
				try {
					Integer id = Integer.parseInt(curriculumId);
					CurriculumEntity ce = curriculumRepository.findById(id).get();
					ce.setSubject(subjectRepository.findById(updateCurriculum.getSubject()).get());
					ce.setYear(yearRepository.findById(updateCurriculum.getYear()).get());
					curriculumRepository.save(ce);
					return new ResponseEntity<CurriculumEntity>(ce, HttpStatus.OK);
	} 
				catch (Exception e) {
					return new ResponseEntity<>(new RESTError(1, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
				}
		 }

}
