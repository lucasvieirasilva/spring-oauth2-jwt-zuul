package com.lvds.core.iam.oauth2;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

public class OAuth2AccessTokenConverter extends DefaultAccessTokenConverter {

	@Override
	public OAuth2Authentication extractAuthentication(final Map<String, ?> map) {
		final OAuth2Authentication authentication = super.extractAuthentication(map);

		final Map<String, Object> applicationInfo = new HashMap<>();

		for (final Map.Entry<String, ?> info : map.entrySet()) {

			if (info.getKey().equals("userId")) {
				applicationInfo.put(info.getKey(), info.getValue());
			} else if (info.getKey().equals("providerId")) {
				applicationInfo.put(info.getKey(), info.getValue());
			} else if (info.getKey().equals("displayName")) {
				applicationInfo.put(info.getKey(), info.getValue());
			}
		}

		authentication.setDetails(applicationInfo);

		return authentication;
	}
}
