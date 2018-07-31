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
import com.iktpreobuka.true_project.entities.PermissionEntity;
import com.iktpreobuka.true_project.entities.SubjectEntity;
import com.iktpreobuka.true_project.entities.TeacherEntity;
import com.iktpreobuka.true_project.entities.dto.PermissionRegisterDTO;
import com.iktpreobuka.true_project.repositories.PermissionRepository;
import com.iktpreobuka.true_project.repositories.SubjectRepository;
import com.iktpreobuka.true_project.repositories.TeacherRepository;

@RestController
@RequestMapping(path = "/permissions")
public class PermissionController {
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	
	 // Metod za unos permission-a
	 @Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createPermission( @RequestBody PermissionRegisterDTO permission) {
		TeacherEntity teacher = teacherRepository.findById(permission.getTeacher()).get();
		SubjectEntity subject = subjectRepository.findById(permission.getSubject()).get();
		PermissionEntity pe = new PermissionEntity();
		pe.setTeacher(teacher);
		pe.setSubject(subject);
		permissionRepository.save(pe);
		return new ResponseEntity<PermissionEntity>(pe, HttpStatus.OK);
	}
	
	//Metod za izlistavanje svih permission-a
	 @Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllPermissions() {
		return new ResponseEntity<>(permissionRepository.findAll(), HttpStatus.OK);
	}
	// Metod za brisanje pojedinih dozvola
	 @Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{permId}")
	public ResponseEntity<?> deletePermission(@PathVariable Integer permId) {
		PermissionEntity permission = permissionRepository.findById(permId).get();
		if (permission == null) {
			return new ResponseEntity<RESTError>(new RESTError(1,"Permission with provided ID not found."),
					HttpStatus.NOT_FOUND);
		}
		permissionRepository.deleteById(permId);
		return new ResponseEntity<PermissionEntity>(permission, HttpStatus.OK);
}
	// Metod za izmenu postojećih permission-a
	 @Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.PUT, value="/{permId}")
	public 	ResponseEntity<?> updatePermission(@PathVariable String permId, @Valid @RequestBody PermissionRegisterDTO updatePermission) {
		try {
			TeacherEntity teacher = teacherRepository.findById(updatePermission.getTeacher()).get();
			SubjectEntity subject = subjectRepository.findById(updatePermission.getSubject()).get();
			PermissionEntity permission=permissionRepository.findById(Integer.parseInt(permId)).get();
			permission.setTeacher(teacher);
			permission.setSubject(subject);
			return new ResponseEntity<PermissionEntity>(permissionRepository.save(permission), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError (1,"Subject not found."),HttpStatus.NOT_FOUND);
		}catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError (2,"Cardinal numbers only."), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity <>(new RESTError(3,"Error"+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);}
		}
	
	// Metod za dobavljanje pojedinih permission-a po id-u
	 @Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.GET, value = "/{permId}")
	public ResponseEntity<?> findPermissionById(@PathVariable String permId){
	try{
		Integer id = Integer.parseInt(permId);
		PermissionEntity permission = permissionRepository.findById(id).get();
		return new ResponseEntity<PermissionEntity>(permission,HttpStatus.OK);
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
