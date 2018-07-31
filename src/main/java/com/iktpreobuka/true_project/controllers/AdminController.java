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
import com.iktpreobuka.true_project.dao.UserDaoImpl;
import com.iktpreobuka.true_project.entities.AdminEntity;
import com.iktpreobuka.true_project.entities.dto.AdminRegisterDTO;
import com.iktpreobuka.true_project.entities.dto.ChangePasswordDTO;
import com.iktpreobuka.true_project.enumerations.EUserRole;
import com.iktpreobuka.true_project.repositories.AdminRepository;

@RestController
@RequestMapping(path = "/admins")
public class AdminController {

	@Autowired
	private UserDaoImpl userDaoImpl;
	
	@Autowired
	private AdminRepository adminRepository;

	// Metod za unos administratora
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addAdmin(@Valid @RequestBody AdminRegisterDTO admin) {
		try {
			AdminEntity ae = new AdminEntity();
			ae.setFirstName(admin.getFirstName());
			ae.setLastName(admin.getLastName());
			ae.setUsername(admin.getUsername());
			ae.setEmail(admin.getEmail());
			ae.setPassword(admin.getPassword());
			ae.setRole(EUserRole.ROLE_ADMIN);
			adminRepository.save(ae);
			return new ResponseEntity<AdminEntity>(ae, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Metod za izlistavanje svih administratora sa njihovim obelezjima
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllAdmins() {
		try {
			return new ResponseEntity<>(adminRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Metod za izmenu podataka za pojedine administratore
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/{adminId}")
	public ResponseEntity<?> updateAdmin(@PathVariable String adminId,
			@Valid @RequestBody AdminRegisterDTO updateAdmin) {
		try {
			Integer id = Integer.parseInt(adminId);
			AdminEntity admin = adminRepository.findById(id).get();
			admin.setFirstName(updateAdmin.getFirstName());
			admin.setLastName(updateAdmin.getLastName());
			admin.setUsername(updateAdmin.getUsername());
			admin.setEmail(updateAdmin.getEmail());
			admin.setPassword(updateAdmin.getPassword());
			return new ResponseEntity<AdminEntity>(adminRepository.save(admin), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1, "Subject not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2, "Cardinal numbers only."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Metod za brisanje pojedinih administratora
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{adminId}")
	public ResponseEntity<?> deleteAdmin(@PathVariable String adminId) {
		try {
			Integer id = Integer.parseInt(adminId);
			AdminEntity admin = adminRepository.findById(id).get();
			adminRepository.deleteById(id);
			return new ResponseEntity<AdminEntity>(admin, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1, "Subject not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2, "Cardinal numbers only."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Metod za prikaz pojedinih administratora po Id
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/{adminId}")
	public ResponseEntity<?> findAdminById(@PathVariable String adminId) {
		try {
			Integer id = Integer.parseInt(adminId);
			AdminEntity admin = adminRepository.findById(id).get();
			return new ResponseEntity<AdminEntity>(admin, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1, "Subject not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2, "Cardinal numbers only."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Metod za izmenu passworda
	@RequestMapping(method = RequestMethod.PUT, value = "changePassword/{adminId}")
	public ResponseEntity<?> changePassword(@PathVariable String adminId,
			@Valid @RequestBody ChangePasswordDTO updatePassword) {
		try {
			Integer id = Integer.parseInt(adminId);
			AdminEntity admin = adminRepository.findById(id).get();
			if (admin.getPassword().equals(updatePassword.getOldPassword())) {
				if (updatePassword.getRepeatNewPassword().equals(updatePassword.getNewPassword()))
					admin.setPassword(updatePassword.getNewPassword());
				else 
					return new ResponseEntity<>(new RESTError(5, "New password not confirmed."), HttpStatus.BAD_REQUEST);	
			} else
				return new ResponseEntity<>(new RESTError(4, "Wrong old password"), HttpStatus.BAD_REQUEST);
			admin = (AdminEntity) userDaoImpl.changePassword(admin);
			// System.out.printf("Moja poruka od %s sa prezimenom %s %nSa mailom %s",admin.getFirstName(),
			//		admin.getLastName(),admin.getEmail()); 
			return new ResponseEntity<AdminEntity>(admin, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1, "Subject not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2, "Cardinal numbers only."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}