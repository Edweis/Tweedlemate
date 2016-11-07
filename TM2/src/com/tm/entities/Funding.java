package com.tm.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Funding extends SuperEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdUser")
	@NotNull
	private User User;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "IdScholarship")
	@NotNull
	private Scholarship Scholarship;
	private String Amount;

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

	public Scholarship getScholarship() {
		return Scholarship;
	}

	public void setScholarship(Scholarship scholarship) {
		Scholarship = scholarship;
	}

	@NotNull(message = "Please insert an amount")
	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

}