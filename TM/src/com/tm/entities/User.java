package com.tm.entities;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;
import org.joda.time.DateTime;

import com.tm.tools.JodaDateTimeConverter;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@NotNull(message = "Please insert an email address")
	@Pattern(regexp = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)", message = "Please select a valide mail address")
	private String Email;
	@NotNull(message = "Please insert an password")
	@Size(min = 5, message = "Your password should have at least 5 characters")
	private String Password;
	@NotNull(message = "Please insert an first name")
	@Size(min = 2, message = "Your name should have at least 2 characters")
	@Pattern(regexp = "[^\\s-]", message = "Your first name should be in one word")
	private String FirstName;

	// Asttributes below are not mandatory
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String PicturePath;
	private String IntroductionText;
	private String AppointmentPrice;

	private Integer RatingResult;
	@Column(columnDefinition = "TIMESTAMP")
	@Converter(name = "dateTimeConverter", converterClass = JodaDateTimeConverter.class)
	@Convert("dateTimeConverter")
	private DateTime DateRegistration;
	@OneToMany(mappedBy = "User")
	private List<Education> myEducation;
	@OneToMany(mappedBy = "User")
	private List<WorkCursus> myWorkcursus;
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Contact Contact;

	/*
	 * Particular methods
	 */

	/**
	 * Two users are equals if they email addresses match.
	 * 
	 * @param user
	 * @return
	 */
	public boolean equals(Object user) {
		if (user == null) {
			return false;
		}
		if (user instanceof User) {
			return ((User) user).getEmail().equals(this.getEmail());
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return FirstName;
	}

	public String getProfilePictureName() {
		return "pp" + this.getId();
	}

	public String getProfileURI() {
		return "/Profile/" + this.getId() + "/" + this.getFirstName();
	}

	public String getAppointmentURI() {
		return "/Appointment/" + this.getId() + "/" + this.getFirstName();
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

	public String getAppointmentPrice() {
		return AppointmentPrice;
	}

	public void setAppointmentPrice(String appointmentPrice) {
		AppointmentPrice = appointmentPrice;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getPicturePath() {
		return PicturePath;
	}

	public void setPicturePath(String picturePath) {
		PicturePath = picturePath;
	}

	public String getIntroductionText() {
		return IntroductionText;
	}

	public void setIntroductionText(String introductionText) {
		IntroductionText = introductionText;
	}

	public Integer getRatingResult() {
		return RatingResult;
	}

	public void setRatingResult(Integer ratingResult) {
		RatingResult = ratingResult;
	}

	public DateTime getDateRegistration() {
		return DateRegistration;
	}

	public void setDateRegistration(DateTime dateRegistration) {
		DateRegistration = dateRegistration;
	}

	public List<Education> getMyEducation() {
		return myEducation;
	}

	public void setMyEducation(List<Education> myEducation) {
		this.myEducation = myEducation;
	}

	public List<WorkCursus> getMyWorkcursus() {
		return myWorkcursus;
	}

	public void setMyWorkcursus(List<WorkCursus> myWorkcursus) {
		this.myWorkcursus = myWorkcursus;
	}

	public Contact getContact() {
		return Contact;
	}

	public void setContact(Contact contact) {
		Contact = contact;
	}

}