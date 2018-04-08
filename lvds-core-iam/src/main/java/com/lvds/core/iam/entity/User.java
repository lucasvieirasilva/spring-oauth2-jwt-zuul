package com.lvds.core.iam.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.lvds.core.entity.AbstractAuditionGuidKeyEntity;

@Entity
public class User extends AbstractAuditionGuidKeyEntity {

	@Column(name = "FirstName", nullable = false)
	private String firstName;

	@Column(name = "LastName", nullable = false)
	private String lastName;

	@Column(name = "UserName", length = 255, unique = true, nullable = false)
	private String userName;

	@Column(name = "Email", length = 255, unique = true, nullable = false)
	private String email;

	@Column(name = "EmailConfirmed")
	private boolean emailConfirmated;

	@Column(name = "PhoneNumber", length = 255)
	private String phoneNumber;

	@Column(name = "PhoneNumberConfirmed")
	private boolean phoneNumberComfirmated;

	@Column(name = "PasswordHash", length = 255, nullable = true)
	private String passwordHash;

	@Column(name = "ProviderId")
	private String providerId;

	@Column(name = "ProviderKey")
	private String providerKey;

	@Column(name = "ProviderAccessToken")
	private String providerAccessToken;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="User_Roles",
	    joinColumns={@JoinColumn(name="UserId",
	     referencedColumnName="Id")},
	    inverseJoinColumns={@JoinColumn(name="RoleId",
	      referencedColumnName="Id")})
	private List<Role> roles = new ArrayList<Role>();

	@Column(name = "Active")
	private boolean active;

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getProviderAccessToken() {
		return providerAccessToken;
	}

	public String getProviderId() {
		return providerId;
	}

	public String getProviderKey() {
		return providerKey;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public String getUserName() {
		return userName;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isEmailConfirmated() {
		return emailConfirmated;
	}

	public boolean isPhoneNumberComfirmated() {
		return phoneNumberComfirmated;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setEmailConfirmated(final boolean emailConfirmated) {
		this.emailConfirmated = emailConfirmated;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public void setPasswordHash(final String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPhoneNumberComfirmated(final boolean phoneNumberComfirmated) {
		this.phoneNumberComfirmated = phoneNumberComfirmated;
	}

	public void setProviderAccessToken(final String providerAccessToken) {
		this.providerAccessToken = providerAccessToken;
	}

	public void setProviderId(final String providerId) {
		this.providerId = providerId;
	}

	public void setProviderKey(final String providerKey) {
		this.providerKey = providerKey;
	}

	public void setRoles(final List<Role> roles) {
		this.roles = roles;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}
}
