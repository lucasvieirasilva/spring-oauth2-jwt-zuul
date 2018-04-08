package com.lvds.auth.config.oauth2;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;

public class UserApprovalHandler extends ApprovalStoreUserApprovalHandler {

	private boolean useApprovalStore = true;

	private ClientDetailsService clientDetailsService;

	@Override
	public AuthorizationRequest checkForPreApproval(AuthorizationRequest authorizationRequest,
			final Authentication userAuthentication) {

		boolean approved = false;

		if (useApprovalStore) {
			authorizationRequest = super.checkForPreApproval(authorizationRequest, userAuthentication);
			approved = authorizationRequest.isApproved();
		} else {
			if (clientDetailsService != null) {
				final Collection<String> requestedScopes = authorizationRequest.getScope();
				try {
					final ClientDetails client = clientDetailsService
							.loadClientByClientId(authorizationRequest.getClientId());
					for (final String scope : requestedScopes) {
						if (client.isAutoApprove(scope)) {
							approved = true;
							break;
						}
					}
				} catch (final ClientRegistrationException e) {
				}
			}
		}
		authorizationRequest.setApproved(approved);

		return authorizationRequest;

	}

	@Override
	public void setClientDetailsService(final ClientDetailsService clientDetailsService) {
		this.clientDetailsService = clientDetailsService;
		super.setClientDetailsService(clientDetailsService);
	}

	public void setUseApprovalStore(final boolean useApprovalStore) {
		this.useApprovalStore = useApprovalStore;
	}

}