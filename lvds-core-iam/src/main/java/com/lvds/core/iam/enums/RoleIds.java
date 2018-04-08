package com.lvds.core.iam.enums;

public enum RoleIds {
	ADMINS("3c717169-e073-4a13-ac2d-e93579360a51"),
	USERS("0d69f85d-fd40-45be-a809-389e8ebba2ec");

	private String id;

	private RoleIds(final String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
