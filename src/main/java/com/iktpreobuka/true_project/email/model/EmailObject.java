package com.iktpreobuka.true_project.email.model;

public class EmailObject {

	public EmailObject(String to, String subject, String text) {
		super();
		this.to = to;
		this.subject = subject;
		this.text = text;
	}

	private String to;
	private String subject;
	private String text;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
