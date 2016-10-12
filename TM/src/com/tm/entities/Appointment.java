package com.tm.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;
import org.joda.time.DateTime;

import com.tm.tools.JodaDateTimeConverter;

@Entity
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@NotNull(message = "Please insert a title to begin the chat")
	@Column(name = "Title")
	private String TitleAppointment;
	@Column(columnDefinition = "TIMESTAMP")
	@Converter(name = "dateTimeConverter", converterClass = JodaDateTimeConverter.class)
	@Convert("dateTimeConverter")
	private DateTime Date;
	private String Place;
	private Integer Price;
	private String Comment;
	private Integer Rating;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdChat")
	private Chat Chat;
	@OneToMany(mappedBy = "Appointment")
	private List<Planning> myPlanning;

	/*
	 * Getters and setters
	 */
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getTitleAppointment() {
		return TitleAppointment;
	}

	public void setTitleAppointment(String titleAppointment) {
		TitleAppointment = titleAppointment;
	}

	public DateTime getDate() {
		return Date;
	}

	public void setDate(DateTime date) {
		Date = date;
	}

	public String getPlace() {
		return Place;
	}

	public void setPlace(String place) {
		Place = place;
	}

	public Integer getPrice() {
		return Price;
	}

	public void setPrice(Integer price) {
		Price = price;
	}

	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		Comment = comment;
	}

	public Integer getRating() {
		return Rating;
	}

	public void setRating(Integer rating) {
		Rating = rating;
	}

	public Chat getChat() {
		return Chat;
	}

	public void setChat(Chat chat) {
		Chat = chat;
	}

	public List<Planning> getMyPlanning() {
		return myPlanning;
	}

	public void setMyPlanning(List<Planning> myPlanning) {
		this.myPlanning = myPlanning;
	}

}