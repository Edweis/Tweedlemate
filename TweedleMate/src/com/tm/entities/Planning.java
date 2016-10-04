package com.tm.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Planning {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdUser")
	private User Participant;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdAppointment")
	private Appointment Appointment;
	private Boolean Ongoing;
	private String Position;
	private String PayementStatus;
	private String Questions;
	private Boolean Ready;
}