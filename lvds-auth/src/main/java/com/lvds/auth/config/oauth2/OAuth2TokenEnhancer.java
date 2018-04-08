package com.lvds.auth.config.oauth2;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.lvds.core.iam.entity.CustomUserDetails;

public class OAuth2TokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(final OAuth2AccessToken accessToken, final OAuth2Authentication authentication) {

		final Map<String, Object> additionalInfo = new HashMap<>();

		if (authentication.getPrincipal() instanceof CustomUserDetails) {
			final CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

			additionalInfo.put("userId", details.getUserId());
			additionalInfo.put("providerId", details.getProviderId());
			additionalInfo.put("displayName", details.getDisplayName());
		}

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

		return accessToken;
	}
}