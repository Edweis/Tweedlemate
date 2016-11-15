package com.tm.entities;

import javax.persistence.Entity;

import com.tm.forms.search.Searchable;

@Entity
public class Country extends SuperEntity {

	@Searchable(userFetchPath = "User.myEducation.School.Country")
	private String Name;
	private String Code2;
	private String Code3;

	@Override
	public String toString() {
		return Name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Country)) {
			return false;
		}
		return (this.getId() == ((Country) obj).getId());

	}

	public String getCode2() {
		return Code2;
	}

	public void setCode2(String code2) {
		Code2 = code2;
	}

	public String getCode3() {
		return Code3;
	}

	public void setCode3(String code3) {
		Code3 = code3;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

}