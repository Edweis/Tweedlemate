package com.tm.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@ManyToMany
	@JoinTable(name = "Chair", joinColumns = @JoinColumn(name = "IdRoom", referencedColumnName = "Id"), inverseJoinColumns = @JoinColumn(name = "IdUser", referencedColumnName = "Id"))
	private List<User> Participants;
	private Boolean Ongoing;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "IdChat")
	private Chat Chat;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "IdAppointment")
	private Appointment Appointment;

	public boolean equals(Object room) {
		if (room == null) {
			return false;
		}
		if (room instanceof Room) {
			return ((Room) room).getId().equals(this.getId());
		} else {
			return false;
		}
	}

	public String participantsToString() {
		String res = "";
		for (User u : Participants) {
			res = res + u.getFirstName() + ", ";
		}
		res = res.substring(0, res.length() - 2);
		return res;
	}

	/*
	 * Getters and setters
	 */
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public List<User> getParticipants() {
		return Participants;
	}

	public void setParticipants(List<User> participants) {
		Participants = participants;
	}

	public Boolean getOngoing() {
		return Ongoing;
	}

	public void setOngoing(Boolean ongoing) {
		Ongoing = ongoing;
	}

	public Chat getChat() {
		return Chat;
	}

	public void setChat(Chat chat) {
		Chat = chat;
	}

	public Appointment getAppointment() {
		return Appointment;
	}

	public void setAppointment(Appointment appointment) {
		Appointment = appointment;
	}

}