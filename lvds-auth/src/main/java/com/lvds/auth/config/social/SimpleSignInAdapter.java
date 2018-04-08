package com.lvds.auth.config.social;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

import com.lvds.core.iam.service.UserService;

public class SimpleSignInAdapter implements SignInAdapter {

	private final RequestCache requestCache;
	private final UserService userService;

	public SimpleSignInAdapter(final RequestCache requestCache, final UserService userService) {
		this.requestCache = requestCache;
		this.userService = userService;
	}

	private String extractOriginalUrl(final NativeWebRequest request) {
		final HttpServletRequest nativeReq = request.getNativeRequest(HttpServletRequest.class);
		final HttpServletResponse nativeRes = request.getNativeResponse(HttpServletResponse.class);
		final SavedRequest saved = requestCache.getRequest(nativeReq, nativeRes);
		if (saved == null) {
			return null;
		}
		requestCache.removeRequest(nativeReq, nativeRes);
		removeAuthenticationAttributes(nativeReq.getSession(false));
		return saved.getRedirectUrl();
	}

	private void removeAuthenticationAttributes(final HttpSession session) {
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

	@Override
	public String signIn(final String localUserId, final Connection<?> connection, final NativeWebRequest request) {
		final UserDetails principal = userService.loadUserByUsername(localUserId);

		SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(principal,
                        null,
                        principal.getAuthorities()));

		return extractOriginalUrl(request);
	}

}