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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.true_project.controllers.util.RESTError;
import com.iktpreobuka.true_project.dao.UserDaoImpl;
import com.iktpreobuka.true_project.entities.PermissionEntity;
import com.iktpreobuka.true_project.entities.SubjectEntity;
import com.iktpreobuka.true_project.entities.TeacherEntity;
import com.iktpreobuka.true_project.entities.dto.ChangePasswordDTO;
import com.iktpreobuka.true_project.entities.dto.TeacherRegisterDTO;
import com.iktpreobuka.true_project.enumerations.EUserRole;
import com.iktpreobuka.true_project.repositories.PermissionRepository;
import com.iktpreobuka.true_project.repositories.SubjectRepository;
import com.iktpreobuka.true_project.repositories.TeacherRepository;

@RestController
@RequestMapping(path = "/teachers")
public class TeacherController {

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private UserDaoImpl userDaoImpl;

	@Autowired
	private PermissionRepository permissionRepository;

	// Metod za unos novih nastavnika
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST)
		public ResponseEntity<?> addTeacher(@Valid @RequestBody TeacherRegisterDTO teacher) {
			try{
				TeacherEntity te = new TeacherEntity();
			te.setFirstName(teacher.getFirstName());
			te.setLastName(teacher.getLastName());
			te.setUsername(teacher.getUsername());
			te.setEmail(teacher.getEmail());
			te.setPassword(teacher.getPassword());
			te.setRole(EUserRole.ROLE_TEACHER);
			teacherRepository.save(te);
			return new ResponseEntity<TeacherEntity>(te, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Metod za izlistavanje svih nastavnika
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllTeachers() {
		try {
			return new ResponseEntity<>(teacherRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Metod za izmenu podataka za pojedine nastavnike
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/{teacherId}")
	public ResponseEntity<?> updateAdmin(@PathVariable String teacherId,
			@Valid @RequestBody TeacherRegisterDTO updateTeacher) {
		try {
			Integer id = Integer.parseInt(teacherId);
			TeacherEntity teacher = teacherRepository.findById(id).get();
			teacher.setFirstName(updateTeacher.getFirstName());
			teacher.setLastName(updateTeacher.getLastName());
			teacher.setUsername(updateTeacher.getUsername());
			teacher.setEmail(updateTeacher.getEmail());
			teacher.setPassword(updateTeacher.getPassword());
			return new ResponseEntity<TeacherEntity>(teacherRepository.save(teacher), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1, "Subject not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2, "Cardinal numbers only."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Metod za brisanje pojedinih nastavnika
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteTeacher(@PathVariable Integer id) {
		TeacherEntity teacher = teacherRepository.findById(id).get();

		if (teacher == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Teacher with provided ID not found."),
					HttpStatus.NOT_FOUND);
		}
		teacherRepository.deleteById(id);
		return new ResponseEntity<TeacherEntity>(teacher, HttpStatus.OK);
	}

	// Metod za prikaz pojedinih nastavnika po Id
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findTeacherById(@PathVariable Integer id) {
		TeacherEntity teacher = teacherRepository.findById(id).get();

		if (teacher.equals(null)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Teacher with provided ID not found."),
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<TeacherEntity>(teacher, HttpStatus.OK);
		}
	}

	// Metod za izmenu passworda
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "changePassword/{teacherId}")
	public ResponseEntity<?> changePassword(@PathVariable String teacherId,
			@Valid @RequestBody ChangePasswordDTO updatePassword) {
		try {
			Integer id = Integer.parseInt(teacherId);
			TeacherEntity teacher = teacherRepository.findById(id).get();
			if (teacher.getPassword().equals(updatePassword.getOldPassword())) {
				if (updatePassword.getRepeatNewPassword().equals(updatePassword.getNewPassword()))
					teacher.setPassword(updatePassword.getNewPassword());
				else
					return new ResponseEntity<>(new RESTError(5, "New password not confirmed."),
							HttpStatus.BAD_REQUEST);
			} else
				return new ResponseEntity<>(new RESTError(4, "Wrong old password"), HttpStatus.BAD_REQUEST);
			teacher = (TeacherEntity) userDaoImpl.changePassword(teacher);
			return new ResponseEntity<TeacherEntity>(teacher, HttpStatus.OK);

		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1, "Subject not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2, "Cardinal numbers only."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Metod za dodelu dozvole za predmet profesoru
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/permId")
	public ResponseEntity<?> addSubjectToTeacher(@PathVariable Integer id, @RequestParam Integer subjId) {
		TeacherEntity teacher = teacherRepository.findById(id).get();
		SubjectEntity subject = subjectRepository.findById(subjId).get();
		PermissionEntity permission = new PermissionEntity();
		permission.setTeacher(teacher);
		permission.setSubject(subject);
		return new ResponseEntity<PermissionEntity>(permissionRepository.save(permission), HttpStatus.OK);
	}

	// Metod za izlistavanje dozvola za profesora
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/permissions/{id}")
	public ResponseEntity<?> getAllPermissionsForATeacher(@PathVariable Integer id) {
		TeacherEntity teacher = teacherRepository.findById(id).get();
		List<PermissionEntity> permissions = permissionRepository.findByTeacherId(teacher.getId());
		return new ResponseEntity<List<PermissionEntity>>(permissions, HttpStatus.OK);
	}

}
