package com.lvds.core.iam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class AuthorizationCode {

	@Id
	@Column(name = "Code", nullable = false)
	private String code;

	@Lob
	@Column(name = "AuthenticationData", nullable = false, length = 2000)
	private byte[] authentication;

	public byte[] getAuthentication() {
		return authentication;
	}

	public String getCode() {
		return code;
	}

	public void setAuthentication(final byte[] authentication) {
		this.authentication = authentication;
	}

	public void setCode(final String code) {
		this.code = code;
	}
}