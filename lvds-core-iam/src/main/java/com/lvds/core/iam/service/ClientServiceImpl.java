package com.lvds.core.iam.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lvds.core.iam.repository.ClientRepository;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Override
	public ClientDetails loadClientByClientId(final String clientId) throws ClientRegistrationException {
		return clientRepository.findByIdAndActiveTrue(clientId).map(client -> {
			final BaseClientDetails details = new BaseClientDetails();

			final List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("CLIENT"));

			final Map<String, String> infos = new HashMap<String, String>();
			infos.put("name", client.getName());

			details.setAdditionalInformation(infos);
			details.setClientId(client.getId());
			details.setClientSecret(client.getClientSecret());
			details.setAuthorizedGrantTypes(client.getGrantTypes());
			details.setScope(client.getScopes());

			if (client.getResources() != null) {
				details.setResourceIds(client.getResources());
			}

			details.setAuthorities(authorities);
			details.setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
			details.setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
			details.setAutoApproveScopes(client.getAutoApprovedScopes());

			return details;
		}).orElseThrow(() -> new ClientRegistrationException("O Cliente não está cadastrado!"));
	}
}
