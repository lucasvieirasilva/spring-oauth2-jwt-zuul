package com.lvds.core.iam.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ScopeApprovalPK implements Serializable {
	protected String userId;
	protected String clientId;
	protected String scope;

	public ScopeApprovalPK() {}

	public ScopeApprovalPK(final String userId, final String clientId, final String scope) {
		this.userId = userId;
		this.clientId = clientId;
		this.scope = scope;
	}
}
