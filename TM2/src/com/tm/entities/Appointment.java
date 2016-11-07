package com.tm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
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
	@PrimaryKeyJoinColumn
	private Room Room;
	@NotNull(message = "Please insert a title to begin the chat")
	@Column(name = "Title")
	private String TitleAppointment;
	@Column(columnDefinition = "TIMESTAMP")
	@Converter(name = "dateTimeConverter", converterClass = JodaDateTimeConverter.class)
	@Convert("dateTimeConverter")
	private DateTime Date;
	private String Place;
	private Integer Price;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Room getRoom() {
		return Room;
	}

	public void setRoom(Room room) {
		Room = room;
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

	private String Comment;
	private Integer Rating;

}