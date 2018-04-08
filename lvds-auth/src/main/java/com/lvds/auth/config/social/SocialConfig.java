package com.lvds.auth.config.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;

import com.lvds.core.iam.service.UserService;
import com.lvds.auth.config.facebook.FacebookConnectionSignup;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;

	@Autowired
	private UsersConnectionRepository usersConnectionRepository;

	@Autowired
	private FacebookConnectionSignup facebookConnectionSignup;

	@Autowired
	private UserService userService;

	@Bean
	public ProviderSignInController providerSignInController() {
		((InMemoryUsersConnectionRepository) usersConnectionRepository).setConnectionSignUp(facebookConnectionSignup);

		final RequestCache requestCache = new HttpSessionRequestCache();

		final ProviderSignInController signInController = new ProviderSignInController(connectionFactoryLocator,
				usersConnectionRepository, new SimpleSignInAdapter(requestCache, userService));

		return signInController;
	}

}
