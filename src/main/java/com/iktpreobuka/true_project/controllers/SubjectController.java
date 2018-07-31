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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.true_project.controllers.util.RESTError;
import com.iktpreobuka.true_project.entities.SubjectEntity;
import com.iktpreobuka.true_project.repositories.SubjectRepository;

@RestController
@RequestMapping(path="/subjects")
public class SubjectController {
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	// Metod za dodavanje predmeta
	@Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.POST)
	public SubjectEntity addNewSubject(@Valid @RequestParam String subjName) {
		SubjectEntity subject = new SubjectEntity();
		subject.setSubjName(subjName);
		subjectRepository.save(subject);
		return subject;
	}
	// Metod za prikaz liste svih predmeta 
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<SubjectEntity> getAllSubjects() {
	return subjectRepository.findAll();
	}
	// Metod za izmenu podataka o predmetu
	@Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.PUT,value="/{Name}")
	public ResponseEntity<?> updateSubject(@PathVariable String Name, @RequestBody SubjectEntity updateSubject){
		SubjectEntity subject = subjectRepository.findBySubjName(Name);
		if (subject == null || updateSubject == null) {
			return new ResponseEntity<RESTError>(new RESTError(1,"Subject with provided name not found."),
					HttpStatus.NOT_FOUND);
		}
		if (updateSubject.getSubjName() != null || !updateSubject.getSubjName().equals(" ")
				|| !updateSubject.getSubjName().equals("")) {
			subject.setSubjName(updateSubject.getSubjName());
		}
		return new ResponseEntity<SubjectEntity>(subjectRepository.save(subject),
				HttpStatus.OK);
	}
	// Metod za brisanje predmeta sa liste
	@Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}")
	public ResponseEntity<?> deleteSubject(@PathVariable Integer id) {
		SubjectEntity subject = subjectRepository.findById(id).get();

		if (subject == null) {
			return new ResponseEntity<RESTError>(new RESTError(1,"Subject with provided ID not found."),
					HttpStatus.NOT_FOUND);
		}
		subjectRepository.deleteById(id);
		return new ResponseEntity<>("Delete successfull", HttpStatus.OK);
	}
	// Metod za nalaženje predmeta po ID
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findSubjectBySubjId(@PathVariable Integer id) {
		SubjectEntity subject = subjectRepository.findById(id).get();

		if (subject == null) {
			return new ResponseEntity<RESTError>(new RESTError(1,"Subject with provided ID not found."),
					HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<SubjectEntity>(subject, HttpStatus.OK);
		}
	}

}
