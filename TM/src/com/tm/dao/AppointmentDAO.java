package com.tm.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tm.entities.Appointment;

@Stateless
public class AppointmentDAO {

	@PersistenceContext
	private EntityManager em;

	public void create(Appointment object) {
		this.em.persist(object);
	}

	public List<Appointment> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Appointment getFromId(Long id) {
		return this.em.find(Appointment.class, id);
	}

	public void remove(Appointment object) {
		// TODO Auto-generated method stub

	}
}
