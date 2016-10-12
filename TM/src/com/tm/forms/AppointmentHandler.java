package com.tm.forms;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.joda.time.DateTime;

import com.tm.dao.AppointmentDAO;
import com.tm.dao.ChatDAO;
import com.tm.dao.MessageDAO;
import com.tm.dao.PlanningtDAO;
import com.tm.entities.Appointment;
import com.tm.entities.Chat;
import com.tm.entities.Message;
import com.tm.entities.Planning;
import com.tm.entities.User;

@ManagedBean
@ViewScoped
public class AppointmentHandler {

	private String titleAppointment;

	private Appointment appointment;
	private Chat chat;
	private com.tm.entities.Message firstMessage;
	private String titleChat;
	private String content;
	/**
	 * the one that initiate the message
	 */
	private User host;
	/**
	 * Writer of the first message
	 */
	private User writer;

	@EJB
	ChatDAO chatDao;
	@EJB
	AppointmentDAO appointmenttDao;
	@EJB
	MessageDAO messageDao;
	@EJB
	PlanningtDAO planningDao;

	public AppointmentHandler() {
		appointment = new Appointment();
		chat = new Chat();
		firstMessage = new Message();
	}

	/**
	 * After all data have been input correctly in appointment, chat and
	 * message; we have to persist all these object in the right order defined
	 * by the structure of the data base. Moreover we have to create plannings.
	 */
	public void handle() {
		binding();
		chatDao.create(chat);
		messageDao.create(firstMessage);
		appointmenttDao.create(appointment);

		Planning p;
		p = addPlanningtoUser(writer, false);
		planningDao.create(p);
		p = addPlanningtoUser(host, true);
		planningDao.create(p);

		FacesMessage message = new FacesMessage("Succès de l'envoi du message !");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * Insert entities in each other an complete what it can.
	 */
	private void binding() {
		appointment.setTitleAppointment(titleAppointment);
		appointment.setChat(chat);
		appointment.setDate(DateTime.now());

		chat.setTitleChat(titleChat);

		firstMessage.setContent(content);
		firstMessage.setChat(chat);
		firstMessage.setDate(DateTime.now());
		firstMessage.setWriter(writer);

	}

	/**
	 * Add a raw planning to a user.
	 * 
	 * @param appointment
	 * @param user
	 * @param isHost
	 * @return
	 */
	private Planning addPlanningtoUser(User user, boolean isHost) {
		Planning res = new Planning();
		res.setAppointment(appointment);
		res.setParticipant(user);
		res.setPosition(isHost ? "HOST" : "MENTEE");

		return res;
	}

	/*
	 * G & S
	 */

	public String getTitleAppointment() {
		return titleAppointment;
	}

	public void setTitleAppointment(String titleAppointment) {
		this.titleAppointment = titleAppointment;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public com.tm.entities.Message getFirstMessage() {
		return firstMessage;
	}

	public void setFirstMessage(com.tm.entities.Message firstMessage) {
		this.firstMessage = firstMessage;
	}

	public String getTitleChat() {
		return titleChat;
	}

	public void setTitleChat(String titleChat) {
		this.titleChat = titleChat;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

}
