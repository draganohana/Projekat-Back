package com.iktpreobuka.true_project.email.services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.true_project.email.model.EmailObject;
import com.iktpreobuka.true_project.services.EmailService;

@RestController
@RequestMapping(path="/")
public class EmailController {
 // iskljuci mail shield na avastu
	
	@Autowired
	private EmailService emailService;
	
	//private static String PATH_TO_ATTACHMENT = "C:\\Users\\PC\\Pictures\\Saved Pictures.rubyford.gif";
	
	@RequestMapping(method=RequestMethod.POST, value="/simpleEmail")
	public String sendSimpleMessage(@RequestBody EmailObject object) {
		if(object==null || object.getTo()==null || object.getText()==null) {
			return null;
			}
		emailService.sendSimpleMessage(object);
		return "Your mail has been sent!";
		}
		
	}

