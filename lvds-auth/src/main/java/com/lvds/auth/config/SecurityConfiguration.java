package com.lvds.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.lvds.core.iam.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/login", "/signin/**", "/signup/**")
				.permitAll()
			.and()
				.exceptionHandling()
				.accessDeniedPage("/login.jsp?authorization_error=true")
			.and()
				.csrf()
				.requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
				.disable()
				.logout()
				.logoutUrl("/logout")
			.and()
				.formLogin()
				.loginProcessingUrl("/login")
				.failureUrl("/login?authentication_error=true")
				.loginPage("/login")
				.permitAll();
	}

	@Bean
	@Primary
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userService);
		daoAuthenticationProvider.setPasswordEncoder(encoder);
		return daoAuthenticationProvider;
	}
}
