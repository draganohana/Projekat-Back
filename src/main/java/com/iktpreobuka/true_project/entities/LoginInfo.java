package com.iktpreobuka.true_project.entities;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginInfo {
	public static Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	public static String email = auth.getName();

}
