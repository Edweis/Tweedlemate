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

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public User getParticipant() {
		return Participant;
	}

	public void setParticipant(User participant) {
		Participant = participant;
	}

	public Appointment getAppointment() {
		return Appointment;
	}

	public void setAppointment(Appointment appointment) {
		Appointment = appointment;
	}

	public Boolean getOngoing() {
		return Ongoing;
	}

	public void setOngoing(Boolean ongoing) {
		Ongoing = ongoing;
	}

	public String getPosition() {
		return Position;
	}

	public void setPosition(String position) {
		Position = position;
	}

	public String getPayementStatus() {
		return PayementStatus;
	}

	public void setPayementStatus(String payementStatus) {
		PayementStatus = payementStatus;
	}

	public String getQuestions() {
		return Questions;
	}

	public void setQuestions(String questions) {
		Questions = questions;
	}

	public Boolean getReady() {
		return Ready;
	}

	public void setReady(Boolean ready) {
		Ready = ready;
	}
}