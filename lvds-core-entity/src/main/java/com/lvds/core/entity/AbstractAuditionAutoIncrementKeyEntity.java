package com.lvds.core.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractAuditionAutoIncrementKeyEntity extends AbstractAuditionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}
}
