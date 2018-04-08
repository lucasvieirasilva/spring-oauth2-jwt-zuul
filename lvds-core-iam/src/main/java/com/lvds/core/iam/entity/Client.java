package com.lvds.core.iam.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.lvds.core.entity.AbstractAuditionGuidKeyEntity;

@Entity
public class Client extends AbstractAuditionGuidKeyEntity {

	@Column(name = "ClientSecret", nullable = false)
	private String clientSecret;

	@Column(name = "Name", nullable = false)
	private String name;

	@ElementCollection
	@CollectionTable(name = "Client_Scopes")
	private List<String> scopes = new ArrayList<String>();

	@ElementCollection
	@CollectionTable(name = "Client_GrantTypes")
	private List<String> grantTypes = new ArrayList<String>();

	@ElementCollection
	@CollectionTable(name = "Client_Resources")
	private List<String> resources = new ArrayList<String>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="Client_Roles",
	    joinColumns={@JoinColumn(name="ClientId",
	     referencedColumnName="Id")},
	    inverseJoinColumns={@JoinColumn(name="RoleId",
	      referencedColumnName="Id")})
	private List<Role> roles = new ArrayList<Role>();

	private Integer accessTokenValiditySeconds;

	private Integer refreshTokenValiditySeconds;

	@Column(name = "Active")
	private boolean active;

	public Integer getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public List<String> getGrantTypes() {
		return grantTypes;
	}
	public String getName() {
		return name;
	}
	public Integer getRefreshTokenValiditySeconds() {
		return refreshTokenValiditySeconds;
	}
	public List<String> getResources() {
		return resources;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public List<String> getScopes() {
		return scopes;
	}
	public boolean isActive() {
		return active;
	}
	public void setAccessTokenValiditySeconds(final Integer accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}
	public void setActive(final boolean active) {
		this.active = active;
	}
	public void setClientSecret(final String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public void setGrantTypes(final List<String> grantTypes) {
		this.grantTypes = grantTypes;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public void setRefreshTokenValiditySeconds(final Integer refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}
	public void setResources(final List<String> resources) {
		this.resources = resources;
	}
	public void setRoles(final List<Role> roles) {
		this.roles = roles;
	}
	public void setScopes(final List<String> scopes) {
		this.scopes = scopes;
	}
}
