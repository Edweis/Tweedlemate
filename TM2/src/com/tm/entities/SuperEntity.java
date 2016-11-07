package com.tm.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class SuperEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	/**
	 * Two entities are equals if their id match.
	 * 
	 * @param entity
	 * @return
	 */
	public boolean equals(Object entity) {
		if (entity == null) {
			return false;
		}
		if (((SuperEntity) entity).getId() == null) {
			return false;
		}
		return ((SuperEntity) entity).getId() == this.getId();
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

}
