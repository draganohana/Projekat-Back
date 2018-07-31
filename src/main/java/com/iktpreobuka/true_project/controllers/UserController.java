

/*import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iktpreobuka.true_project.controllers.util.RESTError;

package com.iktpreobuka.true_project.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.true_project.enumerations.EUserRole;
import com.iktpreobuka.true_project.controllers.util.RESTError;
import com.iktpreobuka.true_project.entities.UserEntity;
import com.iktpreobuka.true_project.entities.dto.UserRegisterDTO;
import com.iktpreobuka.true_project.repositories.UserRepository;

@RestController
@RequestMapping(path = "/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@Valid @RequestBody UserRegisterDTO user) {
	try{	
		UserEntity ue = new UserEntity();
		ue.setFirstName(user.getFirstName());
		ue.setLastName(user.getLastName());
		ue.setUsername(user.getUsername());
		ue.setEmail(user.getEmail());
		ue.setPassword(user.getPassword());
		ue.setRole(user.getRole());
		userRepository.save(ue);
		return new ResponseEntity<UserEntity>(ue, HttpStatus.OK);
	} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsers() {
		try {
			return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3, "Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserEntity updateUser) {
		UserEntity user = userRepository.findById(id).get();
		if (user == null || updateUser == null) {
			return new ResponseEntity<RESTError>(new RESTError("User with provided ID not found."),
					HttpStatus.NOT_FOUND);
		}
		if (updateUser.getFirstName() != null || !updateUser.getFirstName().equals(" ")
				|| !updateUser.getFirstName().equals("")) {
			user.setFirstName(updateUser.getFirstName());
		}
		if (updateUser.getLastName() != null || !updateUser.getLastName().equals(" ")
				|| !updateUser.getLastName().equals("")) {
			user.setLastName(updateUser.getLastName());
		}
		if (updateUser.getUsername() != null || !updateUser.getUsername().equals(" ")
				|| !updateUser.getUsername().equals("")) {
			user.setUsername(updateUser.getUsername());
		}
		if (updateUser.getEmail() != null || !updateUser.getEmail().equals(" ") || !updateUser.getEmail().equals("")) {
			user.setEmail(updateUser.getEmail());
		}
		if (updateUser.getPassword() != null || !updateUser.getPassword().equals(" ")
				|| !updateUser.getPassword().equals("")) {
			user.setPassword(updateUser.getPassword());
		}
		return new ResponseEntity<UserEntity>(userRepository.save(user), HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.PUT, value = "change/{id}/{role}")
	public ResponseEntity<?> changeUserRole(@PathVariable Integer id, @PathVariable String role) {
		UserEntity user = userRepository.findById(id).get();

		if (user == null) {
			return new ResponseEntity<RESTError>(new RESTError("User with provided ID not found."),
					HttpStatus.NOT_FOUND);
		}
		EUserRole userRole = EUserRole.valueOf(role);
		user.setRole(userRole);

		return new ResponseEntity<UserEntity>(userRepository.save(user), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "changePassword/{id}")
	public ResponseEntity<?> changePassword(@PathVariable Integer id, @RequestParam("newPassword") String newPassword,
			@RequestParam("oldPassword") String oldPassword) {
		UserEntity user = userRepository.findById(id).get();

		if (user == null) {
			return new ResponseEntity<RESTError>(new RESTError("User with provided ID not found."),
					HttpStatus.NOT_FOUND);
		}
		if (user.getPassword().equals(oldPassword)) {
			user.setPassword(newPassword);
		}

		return new ResponseEntity<UserEntity>(userRepository.save(user), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		UserEntity user = userRepository.findById(id).get();

		if (user == null) {
			return new ResponseEntity<RESTError>(new RESTError("User with provided ID not found."),
					HttpStatus.NOT_FOUND);
		}
		userRepository.deleteById(id);
		return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findUserById(@PathVariable Integer id) {
		UserEntity user = userRepository.findById(id).get();

		if (user == null) {
			return new ResponseEntity<RESTError>(new RESTError("User with provided ID not found."),
					HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
		}
	}
	@RequestMapping(method = RequestMethod.GET, value = "/by-username/{username}")
	public ResponseEntity<?> findUserByUsername(@PathVariable String username) {
		UserEntity user = userRepository.findByUsername(username);
		if (user == null) {
			return new ResponseEntity<RESTError>(new RESTError("User with provided username not found."),
					HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
		}
	}
}
*/