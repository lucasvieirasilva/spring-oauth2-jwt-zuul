package com.lvds.core.entity;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners({ AuditingEntityListener.class, GuidEntityListener.class })
public abstract class AbstractAuditionGuidKeyEntity extends AbstractAuditionEntity {

	@Id
	@Column(name = "Id")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}
}
