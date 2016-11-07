package com.tm.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.DateTime;

import com.tm.entities.Chat;
import com.tm.entities.Message;

@Stateless
public class MessageDAO {

	private static final String Q_SELECT_AFTER_TIME = "SELECT m FROM Message m WHERE m.Date>?1 AND m.Chat.Id=?2 ORDER BY m.Date DESC";
	private static final String Q_SELECT_ALL = "SELECT m FROM Message m WHERE m.Chat=?1";

	@PersistenceContext
	private EntityManager em;

	public List<Message> getAll(Chat chat) {
		return this.em.createQuery(Q_SELECT_ALL, Message.class).setParameter(1, chat).getResultList();
	}

	public Message getFirstAfter(Chat selectedChat, DateTime lastUpdate) {
		List<Message> res = null;
		try {
			res = this.em.createQuery(Q_SELECT_AFTER_TIME, Message.class).setParameter(1, lastUpdate)
					.setParameter(2, selectedChat.getId()).getResultList();

			if (res.isEmpty()) {
				return null;
			} else {
				Message mmmm = res.get(0);
				return mmmm;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
