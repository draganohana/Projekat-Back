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
import com.iktpreobuka.true_project.entities.GradeTypeEntity;
import com.iktpreobuka.true_project.entities.dto.GradeTypeRegisterDTO;
import com.iktpreobuka.true_project.repositories.GradeTypeRepository;

@RestController
@RequestMapping(path = "/gradeTypes")
public class GradeTypeController {
	
	@Autowired
	private GradeTypeRepository gradeTypeRepository;
	
	//Metod za unos tipova ocena
	 @Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> addGradeType(@Valid @RequestBody GradeTypeRegisterDTO gradeType) {
         GradeTypeEntity gt = new GradeTypeEntity();
         gt.setType(gradeType.getType());
         gradeTypeRepository.save(gt);
         return new ResponseEntity<GradeTypeEntity>(gt, HttpStatus.OK);
	}
	// Metod za izlistavanje svih tipova ocena
	 @Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<GradeTypeEntity> getAllGradeTypes() {
		return gradeTypeRepository.findAll();
}
	// Metod za izmenu tipova ocena
	 @Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/{gTypeId}")
	public ResponseEntity<?> updateGradeType(@PathVariable Integer gTypeId, @RequestBody GradeTypeEntity updateGradeType) {
		try {
			GradeTypeEntity gradeType = gradeTypeRepository.findById(gTypeId).get();

			if (gradeType.getgTypeId().equals(gTypeId)) {
				gradeType.setType(updateGradeType.getType());
				gradeTypeRepository.save(gradeType);
				return new ResponseEntity<GradeTypeEntity>(gradeType, HttpStatus.OK);
			} 
			 
				return new ResponseEntity<RESTError>(new RESTError(1,"Grade Type with provided ID not found."),
					HttpStatus.NOT_FOUND);
			}catch (Exception e) {
				return new ResponseEntity<RESTError>(new RESTError(2,"exception occured:"+ e.getMessage()),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
}
	// Metod za brisanje pojedinih tipova ocena
	 @Secured("ROLE_ADMIN")
			@RequestMapping(method = RequestMethod.DELETE, value = "/{gTypeId}")
			public ResponseEntity<?> deleteGradeType(@PathVariable Integer gTypeId) {
				
				try {
					GradeTypeEntity gradeType = gradeTypeRepository.findById(gTypeId).get();

					if (gradeType.getgTypeId().equals(gTypeId)) {
						gradeTypeRepository.deleteById(gTypeId);
						return new ResponseEntity<GradeTypeEntity>(gradeType, HttpStatus.OK);
					} 
					 
						return new ResponseEntity<RESTError>(new RESTError(1,"Grade Type with provided ID not found."),
							HttpStatus.NOT_FOUND);
					}catch (Exception e) {
						return new ResponseEntity<RESTError>(new RESTError(2,"exception occured:"+ e.getMessage()),
								HttpStatus.INTERNAL_SERVER_ERROR);
					}
				
			}
			// Metod za prikaz pojedinih tipova ocena po ID
	        @Secured("ROLE_ADMIN")
			@RequestMapping(method = RequestMethod.GET, value = "/{gTypeId}")
			public ResponseEntity<?> findGradeTypeById(@PathVariable Integer gTypeId) {
				try {
				GradeTypeEntity gradeType = gradeTypeRepository.findById(gTypeId).get();

				if (gradeType.getgTypeId().equals(gTypeId)) {
					
					return new ResponseEntity<GradeTypeEntity>(gradeType, HttpStatus.OK);
				} 
				 
					return new ResponseEntity<RESTError>(new RESTError(1,"Grade Type with provided ID not found."),
						HttpStatus.NOT_FOUND);
				}catch (Exception e) {
					return new ResponseEntity<RESTError>(new RESTError(2,"exception occured:"+ e.getMessage()),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
}