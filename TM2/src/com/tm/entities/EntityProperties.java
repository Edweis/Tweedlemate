package com.tm.entities;

public abstract class EntityProperties<T> {

	private Long Id;

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof EntityProperties<?>) {
			return ((EntityProperties<?>) obj).getId().equals(this.getId());
		} else {
			return false;
		}
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}
}
