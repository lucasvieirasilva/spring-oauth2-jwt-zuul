package com.lvds.core.iam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.lvds.core.entity.AbstractAuditionGuidKeyEntity;

@Entity
public class Role extends AbstractAuditionGuidKeyEntity {

	@Column(name = "Name", nullable = false)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
