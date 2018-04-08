package com.lvds.core.iam.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.social.security.SocialUserDetails;

@SuppressWarnings("serial")
public class CustomUserDetails extends User implements SocialUserDetails {

	private String displayName;

	private String userId;

	private String providerId;

	private String providerKey;

	private String providerAccessToken;

	public CustomUserDetails(final String displayName, final String username, final String password, final boolean enabled, final boolean accountNonExpired,
			final boolean credentialsNonExpired, final boolean accountNonLocked,
			final Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

		this.displayName = displayName;
	}

	public CustomUserDetails(final String displayName, final String userId, final String username, final String password, final boolean enabled, final boolean accountNonExpired,
			final boolean credentialsNonExpired, final boolean accountNonLocked,
			final Collection<? extends GrantedAuthority> authorities, final String providerId, final String providerKey, final String providerAccessToken) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

		this.userId = userId;
		this.providerId = providerId;
		this.providerKey = providerKey;
		this.providerAccessToken = providerAccessToken;
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
	public String getProviderAccessToken() {
		return providerAccessToken;
	}
	public String getProviderId() {
		return providerId;
	}
	public String getProviderKey() {
		return providerKey;
	}

	@Override
	public String getUserId() {
		return userId;
	}

	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	public void setProviderAccessToken(final String providerAccessToken) {
		this.providerAccessToken = providerAccessToken;
	}

	public void setProviderId(final String providerId) {
		this.providerId = providerId;
	}

	public void setProviderKey(final String providerKey) {
		this.providerKey = providerKey;
	}
}
