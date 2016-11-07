package com.tm.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;
import org.joda.time.DateTime;

import com.tm.tools.JodaDateTimeConverter;

@Entity
public class User extends SuperEntity {

	private String Email;
	private String Password;
	private String FirstName;

	// Asttributes below are not mandatory
	private String PictureExtension;
	private String IntroductionText;
	private String AppointmentPrice;

	private Integer RatingResult;
	@Column(columnDefinition = "TIMESTAMP")
	@Converter(name = "dateTimeConverter", converterClass = JodaDateTimeConverter.class)
	@Convert("dateTimeConverter")
	private DateTime DateRegistration;
	@JoinColumn(name = "IdNationality")
	private Country Nationality;

	@OneToMany(mappedBy = "User")
	private List<Education> myEducation;
	@OneToMany(mappedBy = "User")
	private List<WorkCursus> myWorkcursus;
	@OneToMany(mappedBy = "User")
	private List<Experience> myExperience;
	@OneToMany(mappedBy = "User")
	private List<Funding> myFunding;
	@OneToMany(mappedBy = "User")
	private List<Contact> myContactsInfo;

	/*
	 * Particular methods
	 */
	private static final String ABSOLUT_PATH = "D:\\Documents\\Developpement\\MySpace\\TM2\\WebContent\\assets\\profilePicture\\";
	private static final String RELATIVE_PATH = "assets/profilePicture/";
	private static final String DEFAULT_PIC = "default.jpeg";

	@Override
	public String toString() {
		return FirstName;
	}

	public String getImage() {
		return RELATIVE_PATH + getPicturePath();
	}

	/**
	 * Return the path of the picture to be displayed
	 * 
	 * @return
	 */
	public String getPicturePath() {
		if (PictureExtension != null) {
			return getPicNameWithoutExtension() + "." + getPictureExtension();
		} else {
			return DEFAULT_PIC;
		}
	}

	/**
	 * Return the folder of profile picture
	 * 
	 * @return
	 */
	public String getPictureFolder() {
		return ABSOLUT_PATH;
	}

	/**
	 * return the name of the picture without the extension.
	 * 
	 * @return
	 */
	public String getPicNameWithoutExtension() {
		return "pp" + this.getId().toString();
	}
	/*
	 * Getters and setters
	 */

	public String getAppointmentPrice() {
		return AppointmentPrice;
	}

	public void setAppointmentPrice(String appointmentPrice) {
		AppointmentPrice = appointmentPrice;
	}

	@NotNull(message = "Please insert an email address")
	@Pattern(regexp = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)", message = "Please select a valide mail address")
	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	@NotNull(message = "Please insert an password")
	@Size(min = 5, message = "Your password should have at least 5 characters")
	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	@NotNull(message = "Please insert an first name")
	@Size(min = 2, message = "Your name should have at least 2 characters")
	@Pattern(regexp = "^[a-zA-Z_\\-]+", message = "Your first name can contains - or _ but no space or special caracters.")
	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
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

	public String getPictureExtension() {
		return PictureExtension;
	}

	public void setPictureExtension(String pictureExtension) {
		PictureExtension = pictureExtension;
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

	public List<Contact> getMyContactsInfo() {
		return myContactsInfo;
	}

	public void setMyContactsInfo(List<Contact> myContactsInfo) {
		this.myContactsInfo = myContactsInfo;
	}

	public Country getNationality() {
		return Nationality;
	}

	public void setNationality(Country nationality) {
		Nationality = nationality;
	}

	public List<Experience> getMyExperience() {
		return myExperience;
	}

	public void setMyExperience(List<Experience> myExperience) {
		this.myExperience = myExperience;
	}

	public List<Funding> getMyFunding() {
		return myFunding;
	}

	public void setMyFunding(List<Funding> myFunding) {
		this.myFunding = myFunding;
	}

}