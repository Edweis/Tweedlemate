package com.tm.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tm.forms.search.Searchable;

@Entity
public class Work extends SuperEntity {

	@Searchable(userFetchPath = "User.myWorkcursus.Work")
	private String CompanyName;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Idcountry")
	private Country Country;
	private String City;

	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}

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