package com.iktpreobuka.true_project.services;

import com.iktpreobuka.true_project.email.model.EmailObject;

public interface EmailService {

	void sendSimpleMessage (EmailObject object);
}
