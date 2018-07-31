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
import com.iktpreobuka.true_project.entities.YearEntity;
import com.iktpreobuka.true_project.entities.dto.YearRegisterDTO;
import com.iktpreobuka.true_project.repositories.YearRepository;



@RestController
@RequestMapping(path="/years")
public class YearController {

	@Autowired
	private YearRepository yearRepository;
	
	//Metod za dodavanje razreda
	@Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> createYear(@Valid @RequestBody YearRegisterDTO year) {
		YearEntity ye=new YearEntity();
		ye.setYearName(year.getYearName());
		yearRepository.save(ye);
		return new ResponseEntity<YearEntity>(ye, HttpStatus.OK);
	}
	// Metod za izlistavanje svih razreda
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<YearEntity> getAllYearNames() {
		return yearRepository.findAll();
	}
	// Metod za brisanje razreda
		@Secured("ROLE_ADMIN")
		@RequestMapping(method = RequestMethod.DELETE, value = "/{yearId}")
		public ResponseEntity<?> deleteYear(@PathVariable String yearId) {
			try {
				Integer id = Integer.parseInt(yearId);
				YearEntity year = yearRepository.findById(id).get();
				yearRepository.deleteById(id);
				return new ResponseEntity<YearEntity>(year, HttpStatus.OK);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>(new RESTError(1, "Year not found."), HttpStatus.NOT_FOUND);
			} catch (NumberFormatException e) {
				return new ResponseEntity<>(new RESTError(2, "Cardinal numbers only."), HttpStatus.BAD_REQUEST);
			} catch (Exception e) {
				return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	// Metod za pribavljanje razreda po Id-u
		@Secured("ROLE_ADMIN")
		@RequestMapping(method = RequestMethod.GET, value = "/{yearId}")
		public ResponseEntity<?> findYearById(@PathVariable String yearId) {
			try {
				Integer id = Integer.parseInt(yearId);
				YearEntity year = yearRepository.findById(id).get();
				return new ResponseEntity<YearEntity>(year, HttpStatus.OK);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>(new RESTError(1, "Year not found."), HttpStatus.NOT_FOUND);
			} catch (NumberFormatException e) {
				return new ResponseEntity<>(new RESTError(2, "Cardinal numbers only."), HttpStatus.BAD_REQUEST);
			} catch (Exception e) {
				return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
}
}
