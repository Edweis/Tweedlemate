package com.tm.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.tm.forms.search.Searchable;

@Entity
public class School extends SuperEntity {

	@Searchable(userFetchPath = "User.myEducation.School")
	private String Name;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "IdCountry")
	private Country Country;
	private String City;

	@Override
	public String toString() {
		return Name + " (" + Country.getCode3() + ")";
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	@NotNull(message = "Please insert a country")
	public Country getCountry() {
		return Country;
	}

	public void setCountry(Country country) {
		Country = country;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

}