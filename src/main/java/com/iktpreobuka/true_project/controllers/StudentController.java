package com.iktpreobuka.true_project.controllers;

import java.util.ArrayList;
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
import com.iktpreobuka.true_project.entities.AttendEntity;
import com.iktpreobuka.true_project.entities.ClassEntity;
import com.iktpreobuka.true_project.entities.ParentEntity;
import com.iktpreobuka.true_project.entities.StudentEntity;
import com.iktpreobuka.true_project.entities.dto.ChangePasswordDTO;
import com.iktpreobuka.true_project.entities.dto.StudentRegisterDTO;
import com.iktpreobuka.true_project.entities.dto.SveOceneUcenikaDTO;
import com.iktpreobuka.true_project.enumerations.EUserRole;
import com.iktpreobuka.true_project.repositories.AttendRepository;
import com.iktpreobuka.true_project.repositories.ClassRepository;
import com.iktpreobuka.true_project.repositories.GradeRepository;
import com.iktpreobuka.true_project.repositories.ParentRepository;
import com.iktpreobuka.true_project.repositories.StudentRepository;


@RestController
@RequestMapping(path = "/students")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private ParentRepository parentRepository;
	
	@Autowired
	private ClassRepository classRepository;
	
	@Autowired
	private UserDaoImpl userDaoImpl;
	
	@Autowired
	private GradeRepository gradeRepository;
	
	
	@Autowired
	private AttendRepository attendRepository;

	// Metod za unos novih ucenika
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addStudent(@Valid @RequestBody StudentRegisterDTO student) {
	try {
		StudentEntity se = new StudentEntity();
		se.setFirstName(student.getFirstName());
		se.setLastName(student.getLastName());
		se.setUsername(student.getUsername());
		se.setEmail(student.getEmail());
		se.setPassword(student.getPassword());
		se.setRole(EUserRole.ROLE_STUDENT);
		studentRepository.save(se);
		return new ResponseEntity<StudentEntity>(se, HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

	// Metod za izlistavanje svih ucenika sa njihovim obelezjima
    @Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllStudents() {
		try {
			return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Metod za izmenu podataka za pojedine učenike
    @Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/{studentId}")
	public ResponseEntity<?> updateStudent(@PathVariable String studentId,
			@Valid @RequestBody StudentRegisterDTO updateStudent) {
		try {
			Integer id = Integer.parseInt(studentId);
			StudentEntity student = studentRepository.findById(id).get();
			student.setFirstName(updateStudent.getFirstName());
			student.setLastName(updateStudent.getLastName());
			student.setUsername(updateStudent.getUsername());
			student.setEmail(updateStudent.getEmail());
			student.setPassword(updateStudent.getPassword());
			return new ResponseEntity<StudentEntity>(studentRepository.save(student), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1, "Subject not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2, "Cardinal numbers only."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Metod za brisanje pojedinih ucenika
    @Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable Integer id) {
		StudentEntity student = studentRepository.findById(id).get();

		if (student == null) {
			return new ResponseEntity<RESTError>(new RESTError(1,"Student with provided ID not found."),
					HttpStatus.NOT_FOUND);
		}
		studentRepository.deleteById(id);
		return new ResponseEntity<StudentEntity>(student, HttpStatus.OK);
	}

	// Metod za prikaz pojedinih ucenika po Id
    @Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findStudentById(@PathVariable Integer id) {
		StudentEntity student = studentRepository.findById(id).get();

		if (student == null) {
			return new ResponseEntity<RESTError>(new RESTError(1,"Student with provided ID not found."),
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<StudentEntity>(student, HttpStatus.OK);
		}
	}

	// Metod za izmenu passworda ucenika
	@RequestMapping(method = RequestMethod.PUT, value = "changePassword/{studentId}")
	public ResponseEntity<?> changePassword(@PathVariable String studentId,
			@Valid @RequestBody ChangePasswordDTO updatePassword) {
		try {
			Integer id = Integer.parseInt(studentId);
			StudentEntity student = studentRepository.findById(id).get();
			if (student.getPassword().equals(updatePassword.getOldPassword())) {
				if (updatePassword.getRepeatNewPassword().equals(updatePassword.getNewPassword()))
					student.setPassword(updatePassword.getNewPassword());
				else 
					return new ResponseEntity<>(new RESTError(5, "New password not confirmed."), HttpStatus.BAD_REQUEST);	
			} else
				return new ResponseEntity<>(new RESTError(4, "Wrong old password"), HttpStatus.BAD_REQUEST);
			student = (StudentEntity) userDaoImpl.changePassword(student);
			return new ResponseEntity<StudentEntity>(student, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1, "Subject not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2, "Cardinal numbers only."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//Metod za dodelu roditelja uceniku
	@RequestMapping(method=RequestMethod.PUT,value="/{id}/par")
	public StudentEntity addStudentToParent(@PathVariable Integer id,@RequestParam Integer par) {
		StudentEntity student = studentRepository.findById(id).get();
		ParentEntity parent = parentRepository.findById(par).get();
		student.setParent(parent);
		studentRepository.save(student);
		return student;
	}
	//Metod za dodelu ucenika odeljenju
		@RequestMapping(method=RequestMethod.PUT,value="/{id}/cls")
		public StudentEntity addStudentToClass(@PathVariable Integer id,@RequestParam Integer cls) {
			StudentEntity student = studentRepository.findById(id).get();
			ClassEntity odeljenje = classRepository.findById(cls).get();
			student.setOdeljenje(odeljenje);
			studentRepository.save(student);
			return student;
		}
		// Metod za ispis svih učenika iz istog odeljenja
		@RequestMapping(method=RequestMethod.GET,value="/students/{odeljenje}")
		public List<StudentEntity> getAllStudentsFromClass(@PathVariable Integer odeljenje){
	     ClassEntity cls = classRepository.findById(odeljenje).get();
	     return studentRepository.findByOdeljenje(cls);
				
		}
		/*@Secured("ROLE_STUDENT")
		// Metod za prikaz svih ocena za ucenika po predmetu
		@RequestMapping(method=RequestMethod.GET,value="/grades/{studentId}/{subjId}")
		public ResponseEntity<?> getAllGradesForStudentBySubject(@PathVariable String studentId,@PathVariable Integer subjId){
			Integer id = Integer.valueOf(studentId);
	 if (loginDAO.getId().equals(id)) {
			SubjectEntity se = subjectRepository.findById(subjId).get();
			return new ResponseEntity<List<GradeEntity>>(gradeRepository.findByStudent_idAndAttend_Permission_Subject(id, se),HttpStatus.OK);
			}
			else 
				return new ResponseEntity<RESTError>(new RESTError(2, "Wrong student ID."), HttpStatus.BAD_REQUEST);
			
		}*/
		// Metod za prikaz svih ocena učenika po predmetima
		@Secured({ "ROLE_STUDENT", "ROLE_ADMIN", "ROLE_PARENT" })
		@RequestMapping(method = RequestMethod.GET, value = "/student/{student_id}") 
		public ResponseEntity<?> getGradesBySubjects(@PathVariable String student_id) {

			if (!student_id.matches("\\d+")) {
				return new ResponseEntity<RESTError>(new RESTError(2, "Provided ID is not number"), HttpStatus.NOT_FOUND);
			}
			if (!studentRepository.findById(Integer.parseInt(student_id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError(1, "Student with provided ID not found"), HttpStatus.NOT_FOUND);
			}

			StudentEntity student = studentRepository.findById(Integer.parseInt(student_id)).get();

	
			ClassEntity cls = new ClassEntity();
			List<SveOceneUcenikaDTO> sou = new ArrayList<>();

			cls = student.getOdeljenje();

			if (cls == null) {
				return new ResponseEntity<RESTError>(new RESTError(1,"Student doesnt belong to any class"), HttpStatus.NOT_FOUND);
			}

			List<AttendEntity> attends = (attendRepository.findByCls(cls));

			for (AttendEntity attend : attends) {
				SveOceneUcenikaDTO temp = new SveOceneUcenikaDTO();
				temp.setSubject(attend.getPermission().getSubject());
				temp.setGrades(gradeRepository.findByStudentAndAttend_Permission_Subject(student, temp.getSubject()));
			    sou.add(temp);
			}

			return new ResponseEntity<List<SveOceneUcenikaDTO>>(sou, HttpStatus.OK);
		}
}
