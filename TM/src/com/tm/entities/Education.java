package com.tm.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;

@Entity
public class Education {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdUser")
	private User User;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdSchool")
	private School School;
	private Integer DurationMonth;
	private Integer StartYear;
	private String Promotion;
	private String Major;
	private String Scholarship;
	private Boolean IsHomeUniversity;
	private Boolean IsCurrentEducation;
	@Pattern(regexp = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)", message = "Please select a valide mail address")
	private String VerifEmail;
	private Boolean IsEmailVerified;

	@Override
	public String toString() {
		if (Major == null) {
			return School.toString();
		} else {
			return School.toString() + " - " + Major;
		}
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

	public School getSchool() {
		return School;
	}

	public void setSchool(School school) {
		School = school;
	}

	public Integer getDurationMonth() {
		return DurationMonth;
	}

	public void setDurationMonth(Integer durationMonth) {
		DurationMonth = durationMonth;
	}

	public Integer getStartYear() {
		return StartYear;
	}

	public void setStartYear(Integer startYear) {
		StartYear = startYear;
	}

	public String getPromotion() {
		return Promotion;
	}

	public void setPromotion(String promotion) {
		Promotion = promotion;
	}

	public String getMajor() {
		return Major;
	}

	public void setMajor(String major) {
		Major = major;
	}

	public String getScholarship() {
		return Scholarship;
	}

	public void setScholarship(String scholarship) {
		Scholarship = scholarship;
	}

	public Boolean getIsHomeUniversity() {
		return IsHomeUniversity;
	}

	public void setIsHomeUniversity(Boolean isHomeUniversity) {
		IsHomeUniversity = isHomeUniversity;
	}

	public Boolean getIsCurrentEducation() {
		return IsCurrentEducation;
	}

	public void setIsCurrentEducation(Boolean isCurrentEducation) {
		IsCurrentEducation = isCurrentEducation;
	}

	public String getVerifEmail() {
		return VerifEmail;
	}

	public void setVerifEmail(String verifEmail) {
		VerifEmail = verifEmail;
	}

	public Boolean getIsEmailVerified() {
		return IsEmailVerified;
	}

	public void setIsEmailVerified(Boolean isEmailVerified) {
		IsEmailVerified = isEmailVerified;
	}

}