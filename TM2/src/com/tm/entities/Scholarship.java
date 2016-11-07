package com.tm.entities;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Scholarship extends SuperEntity {

	private String Title;
	private String Year;
	private String Ref;

	@Override
	public String toString() {
		return Title;
	}

	@NotNull(message = "Please insert a title")
	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	@NotNull(message = "Please insert a year")
	public String getYear() {
		return Year;
	}

	public void setYear(String year) {
		Year = year;
	}

	public String getRef() {
		return Ref;
	}

	public void setRef(String ref) {
		Ref = ref;
	}

}
