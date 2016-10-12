package com.tm.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tm.entities.Chat;

@Stateless
public class ChatDAO {

	@PersistenceContext(unitName = "db_tm_PU")
	private EntityManager em;

	public void create(Chat object) {
		this.em.persist(object);
	}

	public List<Chat> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Chat getFromId(Long id) {
		return this.em.find(Chat.class, id);
	}

	public void remove(Chat object) {
		// TODO Auto-generated method stub

	}

}
