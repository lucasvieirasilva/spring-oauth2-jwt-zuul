package com.lvds.core.entity.auditor;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppAuditorAware implements AuditorAware<String> {

	public String getCurrentAuditor() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return "SYSTEM";
		}

		return authentication.getName();
	}
}