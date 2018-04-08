package com.lvds.auth.config.oauth2;

import java.util.UUID;

import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;

import com.lvds.core.iam.entity.AuthorizationCode;
import com.lvds.core.iam.repository.AuthorizationCodeRepository;

public class JpaAuthorizationCodeServices implements AuthorizationCodeServices {

	private final AuthorizationCodeRepository repository;

	public JpaAuthorizationCodeServices(final AuthorizationCodeRepository repository) {
		this.repository = repository;
	}

	@Override
	public OAuth2Authentication consumeAuthorizationCode(final String code) throws InvalidGrantException {
		OAuth2Authentication authentication = null;

		final AuthorizationCode store = repository.findOne(code);

		if (store != null) {
			authentication = SerializationUtils.deserialize(store.getAuthentication());

			if (authentication != null) {
				repository.delete(store);
			}
		}

		return authentication;
	}

	@Override
	public String createAuthorizationCode(final OAuth2Authentication authentication) {
		final String code = UUID.randomUUID().toString();
		final byte[] authenticationData = SerializationUtils.serialize(authentication);

		final AuthorizationCode store = new AuthorizationCode();
		store.setCode(code);
		store.setAuthentication(authenticationData);

		repository.save(store);

		return code;
	}
}