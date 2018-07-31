package com.iktpreobuka.true_project.controllers;

import java.util.NoSuchElementException;

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
import com.iktpreobuka.true_project.entities.ClassEntity;
import com.iktpreobuka.true_project.entities.YearEntity;
import com.iktpreobuka.true_project.entities.dto.ClassRegisterDTO;
import com.iktpreobuka.true_project.repositories.ClassRepository;
import com.iktpreobuka.true_project.repositories.YearRepository;

@RestController
@RequestMapping(path="/classes")
public class ClassController {
	
	@Autowired
	private ClassRepository classRepository;
	
	@Autowired
	private YearRepository yearRepository;
	
	//Metod za dodavanje odeljenja
	 @Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createClass(@Valid @RequestBody ClassRegisterDTO cls) {
		ClassEntity ce = new ClassEntity();
		ce.setYear(yearRepository.findById(cls.getYear()).get());
		ce.setClassNum(cls.getClassNum());
		classRepository.save(ce);
		return new ResponseEntity<ClassEntity>(ce, HttpStatus.OK);
	}
	// Metod za izlistavanje svih odeljenja
	 @Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllClasses(){
		return new ResponseEntity<Iterable<ClassEntity>>(classRepository.findAll(), HttpStatus.OK);
	
	}
	// Metod za brisanje pojedinih odeljenja
	 @Secured("ROLE_ADMIN")
			@RequestMapping(method = RequestMethod.DELETE, value = "/{classId}")
			public ResponseEntity<?> deleteClass(@PathVariable Integer classId) {
				ClassEntity cls = classRepository.findById(classId).get();

				if (cls == null) {
					return new ResponseEntity<RESTError>(new RESTError(1,"Class with provided ID not found."),
							HttpStatus.NOT_FOUND);
				}
				classRepository.deleteById(classId);
				return new ResponseEntity<ClassEntity>(cls, HttpStatus.OK);
			}
			//Metod za izmene podataka u pojedinim odeljenjima
	        @Secured("ROLE_ADMIN")
			@RequestMapping(method=RequestMethod.PUT, value="/{classId}")
			public 	ResponseEntity<?> updateClass(@PathVariable String classId, @Valid @RequestBody ClassRegisterDTO updateClass) {
				try {
					Integer id = Integer.parseInt(classId);
					ClassEntity cls = classRepository.findById(id).get();
					YearEntity year = yearRepository.findById(updateClass.getYear()).get();
					cls.setYear(year);
					cls.setClassNum(updateClass.getClassNum());
					return new ResponseEntity<ClassEntity>(classRepository.save(cls), HttpStatus.OK);
				} catch (NoSuchElementException e) {
					return new ResponseEntity<>(new RESTError (1,"Subject not found."),HttpStatus.NOT_FOUND);
				}catch (NumberFormatException e) {
					return new ResponseEntity<>(new RESTError (2,"Cardinal numbers only."), HttpStatus.BAD_REQUEST);
				}
				catch (Exception e) {
					return new ResponseEntity <>(new RESTError(3,"Error"+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			//Metod za izlistavanje odeljenja po ID-u
	        @Secured("ROLE_ADMIN")
			@RequestMapping(method=RequestMethod.GET, value = "/{classId}")
			public ResponseEntity<?> findClassById(@PathVariable String classId){
			try{
				Integer id = Integer.parseInt(classId);
				ClassEntity cls = classRepository.findById(id).get();
				return new ResponseEntity<ClassEntity>(cls,HttpStatus.OK);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>(new RESTError (1,"Subject not found."),HttpStatus.NOT_FOUND);
			}catch (NumberFormatException e) {
				return new ResponseEntity<>(new RESTError (2,"Cardinal numbers only."), HttpStatus.BAD_REQUEST);
			}
			catch (Exception e) {
				return new ResponseEntity <>(new RESTError(3,"Error"+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			}
}
