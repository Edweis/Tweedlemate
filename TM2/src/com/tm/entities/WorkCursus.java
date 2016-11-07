package com.tm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;
import org.joda.time.DateTime;

import com.tm.tools.JodaDateTimeConverter;

@Entity
public class WorkCursus extends SuperEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdUser")
	private User User;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdWork")
	private Work Work;
	@Column(columnDefinition = "TIMESTAMP")
	@Converter(name = "dateTimeConverter", converterClass = JodaDateTimeConverter.class)
	@Convert("dateTimeConverter")
	private DateTime StartDate;
	private Integer DurationMonth;
	private String Position;
	private Boolean IsCurrentWork;

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

	public Work getWork() {
		return Work;
	}

	public void setWork(Work work) {
		Work = work;
	}

	public DateTime getStartDate() {
		return StartDate;
	}

	public void setStartDate(DateTime startDate) {
		StartDate = startDate;
	}

	public Integer getDurationMonth() {
		return DurationMonth;
	}

	public void setDurationMonth(Integer durationMonth) {
		DurationMonth = durationMonth;
	}

	public String getPosition() {
		return Position;
	}

	public void setPosition(String position) {
		Position = position;
	}

	public Boolean getIsCurrentWork() {
		return IsCurrentWork;
	}

	public void setIsCurrentWork(Boolean isCurrentWork) {
		IsCurrentWork = isCurrentWork;
	}

}