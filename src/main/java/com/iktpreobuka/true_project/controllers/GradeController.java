package com.iktpreobuka.true_project.controllers;

import java.util.Date;

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
import com.iktpreobuka.true_project.email.model.EmailObject;
import com.iktpreobuka.true_project.entities.AttendEntity;
import com.iktpreobuka.true_project.entities.GradeEntity;
import com.iktpreobuka.true_project.entities.GradeTypeEntity;
import com.iktpreobuka.true_project.entities.StudentEntity;
import com.iktpreobuka.true_project.entities.dto.EditGradeDTO;
import com.iktpreobuka.true_project.entities.dto.GradeRegisterDTO;
import com.iktpreobuka.true_project.repositories.AttendRepository;
import com.iktpreobuka.true_project.repositories.GradeRepository;
import com.iktpreobuka.true_project.repositories.GradeTypeRepository;
import com.iktpreobuka.true_project.repositories.StudentRepository;
import com.iktpreobuka.true_project.services.EmailService;


@RestController
@RequestMapping(path="/grades")
public class GradeController {
	
	@Autowired
	private GradeRepository gradeRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private AttendRepository attendRepository;
	
	@Autowired
	private GradeTypeRepository gradeTypeRepository;
	
	@Autowired
	private EmailService emailService;
	
	//Metod za unos ocena
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createGrade(@Valid @RequestBody GradeRegisterDTO grade) {
		StudentEntity student = studentRepository.findById(grade.getStudent()).get();
		AttendEntity attend = attendRepository.findById(grade.getAttend()).get();
		GradeEntity ge = new GradeEntity();
		GradeTypeEntity gt = gradeTypeRepository.findById(grade.getGradeType()).get();
		
		ge.setGradeValue(grade.getGradeValue());
		ge.setStudent(student);
		ge.setAttend(attend);
		ge.setGradeType(gt);
		ge.setDate(new Date());
		gradeRepository.save(ge);
		EmailObject mail = new EmailObject(student.getParent().getEmail(), "nova ocena", text(ge));
		emailService.sendSimpleMessage(mail);
		return new ResponseEntity<GradeEntity>(ge, HttpStatus.OK);
	}
	// Metod za izlistavanje svih ocena
	  @Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<GradeEntity> getAllGrades() {
		return gradeRepository.findAll();
	}
	
	// Metod za izmenu ocene
		@RequestMapping(method=RequestMethod.PUT, value="/{gradeId}")
		public 	ResponseEntity<?> editGrade(@PathVariable String gradeId, @Valid @RequestBody EditGradeDTO editGrade){
			try {
				Integer id = Integer.parseInt(gradeId);
				GradeEntity ge = gradeRepository.findById(id).get();
				ge.setGradeValue(editGrade.getGradeValue());
				ge.setGradeType(gradeTypeRepository.findById(editGrade.getGradeType()).get());
				return new ResponseEntity<GradeEntity>(gradeRepository.save(ge), HttpStatus.OK);
		}				
		 catch (Exception e) {
			 return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
				}
	
	// Metod za brisanje pojedinih ocena
			@RequestMapping(method = RequestMethod.DELETE, value = "/{gradeId}")
			public ResponseEntity<?> deleteGrade(@PathVariable String gradeId) {
			   try {	
				Integer id = Integer.parseInt(gradeId);
				GradeEntity ge = gradeRepository.findById(id).get();

				if (ge == null) {
					return new ResponseEntity<RESTError>(new RESTError(1,"Grade with provided ID not found."),
							HttpStatus.NOT_FOUND);
				}
				gradeRepository.deleteById(id);
				return new ResponseEntity<GradeEntity>(ge, HttpStatus.OK);
			}
			   catch (Exception e) {
				   return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			   }
				   
			   }
			// Metod za pribavljanje ocene po Id-u
			@Secured("ROLE_ADMIN")
			@RequestMapping(method = RequestMethod.GET, value = "/{gradeId}")
			public ResponseEntity<?> getGradeById(@PathVariable String gradeId) {
				Integer id = Integer.parseInt(gradeId);
				GradeEntity ge = gradeRepository.findById(id).get();
				return new ResponseEntity<GradeEntity>(ge, HttpStatus.OK);
			}
			private String text(GradeEntity ge) {
				return "Poštovani, dana " + ge.getDate() + " učenik " + ge.getStudent().getFirstName() + " " + ge.getStudent().getLastName()
						+ " dobio je ocenu " + ge.getGradeValue() + " iz predmeta "
						+ ge.getAttend().getPermission().getSubject().getSubjName() + " kod nastavnika "
						+ ge.getAttend().getPermission().getTeacher().getFirstName() + " "
						+ ge.getAttend().getPermission().getTeacher().getLastName() + ".";
			}
			
		
			
        
}
