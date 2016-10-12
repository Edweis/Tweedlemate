package com.tm.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String Phone;
	private String Skype;
	private String EmailContact;
	private String Kakao;
	private String Whatsap;
	private String Other;
}