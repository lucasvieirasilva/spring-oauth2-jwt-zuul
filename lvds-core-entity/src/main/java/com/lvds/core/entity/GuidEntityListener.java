package com.lvds.core.entity;

import java.util.UUID;

import javax.persistence.PrePersist;

public class GuidEntityListener {

	@PrePersist
	public void onGuidPrePersist(final AbstractAuditionGuidKeyEntity entity) {
		if (entity.getId() == null) {
			entity.setId(UUID.randomUUID().toString());
		}
	}
}
