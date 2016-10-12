package com.tm.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tm.entities.Message;

@Stateless
public class MessageDAO {

	@PersistenceContext(unitName = "db_tm_PU")
	private EntityManager em;

	public void create(Message object) {
		this.em.persist(object);
	}

	public List<Message> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Message getFromId(Long id) {
		return this.em.find(Message.class, id);
	}

	public void remove(Message object) {
		// TODO Auto-generated method stub

	}

}
