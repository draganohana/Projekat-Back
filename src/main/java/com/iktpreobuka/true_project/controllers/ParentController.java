package com.iktpreobuka.true_project.controllers;

import java.util.List;
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
import com.iktpreobuka.true_project.dao.UserDaoImpl;
import com.iktpreobuka.true_project.entities.GradeEntity;
import com.iktpreobuka.true_project.entities.ParentEntity;
import com.iktpreobuka.true_project.entities.StudentEntity;
import com.iktpreobuka.true_project.entities.SubjectEntity;
import com.iktpreobuka.true_project.entities.dto.ChangePasswordDTO;
import com.iktpreobuka.true_project.entities.dto.ParentRegisterDTO;
import com.iktpreobuka.true_project.enumerations.EUserRole;
import com.iktpreobuka.true_project.repositories.GradeRepository;
import com.iktpreobuka.true_project.repositories.ParentRepository;
import com.iktpreobuka.true_project.repositories.StudentRepository;
import com.iktpreobuka.true_project.repositories.SubjectRepository;
import com.iktpreobuka.true_project.services.LoginDAO;

@RestController
@RequestMapping(path = "/parents")
public class ParentController {

	@Autowired
	private ParentRepository parentRepository;

	@Autowired
	private UserDaoImpl userDaoImpl;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private GradeRepository gradeRepository;
	
	@Autowired
	private LoginDAO loginDAO;

	// Metod za unos novih roditelja
	 @Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addParent(@Valid @RequestBody ParentRegisterDTO parent) {
		try {	
		ParentEntity pe = new ParentEntity();
		pe.setFirstName(parent.getFirstName());
		pe.setLastName(parent.getLastName());
		pe.setUsername(parent.getUsername());
		pe.setEmail(parent.getEmail());
		pe.setPassword(parent.getPassword());
		pe.setRole(EUserRole.ROLE_PARENT);
		parentRepository.save(pe);
		return new ResponseEntity<ParentEntity>(pe, HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

	// Metod za izlistavanje svih roditelja sa njihovim obelezjima
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllParents() {
		try {
			return new ResponseEntity<>(parentRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// Metod za izmenu podataka za pojedine roditelje
	 @Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/{parentId}")
	public ResponseEntity<?> updateParent(@PathVariable String parentId,
			@Valid @RequestBody ParentRegisterDTO updateParent) {
		try {
			Integer id = Integer.parseInt(parentId);
			ParentEntity parent = parentRepository.findById(id).get();
			parent.setFirstName(updateParent.getFirstName());
			parent.setLastName(updateParent.getLastName());
			parent.setUsername(updateParent.getUsername());
			parent.setEmail(updateParent.getEmail());
			parent.setPassword(updateParent.getPassword());
			return new ResponseEntity<ParentEntity>(parentRepository.save(parent), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1, "Subject not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2, "Cardinal numbers only."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Metod za brisanje pojedinih roditelja
	 @Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteParent(@PathVariable Integer id) {
		ParentEntity parent = parentRepository.findById(id).get();

		if (parent == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Parent with provided ID not found."),
					HttpStatus.NOT_FOUND);
		}
		parentRepository.deleteById(id);
		return new ResponseEntity<ParentEntity>(parent, HttpStatus.OK);
	}

	// Metod za prikaz pojedinih roditelja po Id
	 @Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findParentById(@PathVariable Integer id) {
		ParentEntity parent = parentRepository.findById(id).get();

		if (parent == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Parent with provided ID not found."),
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<ParentEntity>(parent, HttpStatus.OK);
		}
	}

	// Metod za izmenu passworda roditelja
	@RequestMapping(method = RequestMethod.PUT, value = "changePassword/{parentId}")
	public ResponseEntity<?> changePassword(@PathVariable String parentId,
			@Valid @RequestBody ChangePasswordDTO updatePassword) {
		try {
			Integer id = Integer.parseInt(parentId);
			ParentEntity parent = parentRepository.findById(id).get();
			if (parent.getPassword().equals(updatePassword.getOldPassword())) {
				if (updatePassword.getRepeatNewPassword().equals(updatePassword.getNewPassword()))
					parent.setPassword(updatePassword.getNewPassword());
				else
					return new ResponseEntity<>(new RESTError(5, "New password not confirmed."),
							HttpStatus.BAD_REQUEST);
			} else
				return new ResponseEntity<>(new RESTError(4, "Wrong old password"), HttpStatus.BAD_REQUEST);
			parent = (ParentEntity) userDaoImpl.changePassword(parent);
			return new ResponseEntity<ParentEntity>(parent, HttpStatus.OK);

		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1, "Subject not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2, "Cardinal numbers only."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// Metod za prikaz svih ocena po predmetu za ucenika koji je potomak roditelja
	        @Secured("ROLE_PARENT")
			@RequestMapping(method=RequestMethod.GET,value="/grades/{parentId}/{subjId}")
			public ResponseEntity<?> getAllGradesForStudentBySubject(@PathVariable String parentId,@PathVariable Integer subjId){
				Integer id = Integer.valueOf(parentId);
		 if (loginDAO.getId().equals(id)) {
			    StudentEntity student = studentRepository.findByParentId(id);
			    Integer studentId = student.getId();
				SubjectEntity se = subjectRepository.findById(subjId).get();
				return new ResponseEntity<List<GradeEntity>>(gradeRepository.findByStudent_idAndAttend_Permission_Subject(studentId, se),HttpStatus.OK);
				}
				else 
					return new ResponseEntity<RESTError>(new RESTError(2, "Wrong parent ID."), HttpStatus.BAD_REQUEST);
				
			}
}
