package com.lvds.core.iam.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus;

@Entity
@IdClass(ScopeApprovalPK.class)
public class ScopeApproval {

	@Id
	private String userId;

	@Id
	private String clientId;

	@Id
	private String scope;

	private ApprovalStatus status;

	private Date expiresAt;

	private Date lastUpdatedAt;

	public String getClientId() {
		return clientId;
	}

	public Date getExpiresAt() {
		return expiresAt;
	}

	public Date getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public String getScope() {
		return scope;
	}

	public ApprovalStatus getStatus() {
		return status;
	}

	public String getUserId() {
		return userId;
	}

	public void setClientId(final String clientId) {
		this.clientId = clientId;
	}

	public void setExpiresAt(final Date expiresAt) {
		this.expiresAt = expiresAt;
	}

	public void setLastUpdatedAt(final Date lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}

	public void setScope(final String scope) {
		this.scope = scope;
	}

	public void setStatus(final ApprovalStatus status) {
		this.status = status;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}
}
