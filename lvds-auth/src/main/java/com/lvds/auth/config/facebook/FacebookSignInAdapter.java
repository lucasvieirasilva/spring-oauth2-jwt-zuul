package com.lvds.auth.config.facebook;

import java.util.Arrays;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

public class FacebookSignInAdapter implements SignInAdapter {
	@Override
	public String signIn(final String localUserId, final Connection<?> connection, final NativeWebRequest request) {

		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
				connection.getDisplayName(), null, Arrays.asList(new SimpleGrantedAuthority("FACEBOOK_USER"))));

		return null;
	}
}