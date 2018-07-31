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
import com.iktpreobuka.true_project.entities.AttendEntity;
import com.iktpreobuka.true_project.entities.ClassEntity;
import com.iktpreobuka.true_project.entities.PermissionEntity;
import com.iktpreobuka.true_project.entities.dto.AttendRegisterDTO;
import com.iktpreobuka.true_project.repositories.AttendRepository;
import com.iktpreobuka.true_project.repositories.ClassRepository;
import com.iktpreobuka.true_project.repositories.PermissionRepository;

@RestController
@RequestMapping(path = "/attend")
public class AttendController {
	
	@Autowired
	private AttendRepository attendRepository;
	
	@Autowired
	private ClassRepository classRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	// Metod za unos attendova
	 @Secured("ROLE_ADMIN")
		@RequestMapping(method = RequestMethod.POST)
		public ResponseEntity<?> createAttend( @RequestBody AttendRegisterDTO attend) {
			ClassEntity cls = classRepository.findById(attend.getCls()).get();
			PermissionEntity permission = permissionRepository.findById(attend.getPermission()).get();
			AttendEntity ae = new AttendEntity();
			ae.setCls(cls);
			ae.setPermission(permission);
			attendRepository.save(ae);
			return new ResponseEntity<AttendEntity>(ae, HttpStatus.OK);
		}
	
	//Metod za izlistavanje svih attendova
	 @Secured("ROLE_ADMIN")
			@RequestMapping(method = RequestMethod.GET)
			public ResponseEntity<?> getAllAttends() {
				return new ResponseEntity<>(attendRepository.findAll(), HttpStatus.OK);
			}
	 
	 // Metod za brisanje pojedinih attend-ova
	 @Secured("ROLE_ADMIN")
	 @RequestMapping(method = RequestMethod.DELETE, value = "/{attendId}")
	 public ResponseEntity<?> deleteAttend(@PathVariable String attendId) {
		 try {
				Integer id = Integer.parseInt(attendId);
				AttendEntity ae = attendRepository.findById(id).get();
				if (ae == null) {
					return new ResponseEntity<RESTError>(new RESTError(1,"Attend with provided ID not found."),
							HttpStatus.NOT_FOUND);
				}
				attendRepository.deleteById(id);
				return new ResponseEntity<AttendEntity>(ae, HttpStatus.OK);
	 }
		 catch (Exception e) {
			 return new ResponseEntity<>(new RESTError(1, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
}
	 // Metod za izlistavanje attenda po Id-ju
	 @Secured("ROLE_ADMIN")
		@RequestMapping(method = RequestMethod.GET, value = "/{attendId}")
		public ResponseEntity<?> getAttendById(@PathVariable String attendId) {
			Integer id = Integer.parseInt(attendId);
			AttendEntity ae = attendRepository.findById(id).get();
			return new ResponseEntity<AttendEntity>(ae, HttpStatus.OK);
		}

	
// Metod za izmenu pojedinih attend-ova
	 @Secured("ROLE_ADMIN")
		@RequestMapping(method = RequestMethod.PUT, value = "/{attendId}")
		public ResponseEntity<?> updateAttend(@PathVariable String attendId,
				@Valid @RequestBody AttendRegisterDTO updateAttend) {
			try {
				Integer id = Integer.parseInt(attendId);
				AttendEntity ae = attendRepository.findById(id).get();
				ae.setCls(classRepository.findById(updateAttend.getCls()).get());
				ae.setPermission(permissionRepository.findById(updateAttend.getPermission()).get());
				attendRepository.save(ae);
				return new ResponseEntity<AttendEntity>(ae, HttpStatus.OK);
} 
			catch (Exception e) {
				return new ResponseEntity<>(new RESTError(1, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
	 }
}
