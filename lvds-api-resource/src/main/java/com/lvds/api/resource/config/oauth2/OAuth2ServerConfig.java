package com.lvds.api.resource.config.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.lvds.core.iam.oauth2.OAuth2AccessTokenConverter;

@Configuration
public class OAuth2ServerConfig extends ResourceServerConfigurerAdapter {

	@Configuration
	@EnableResourceServer
	protected static class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
		@Autowired
		private ResourceServerTokenServices tokenServices;

		@Override
		public void configure(final HttpSecurity http) throws Exception {
			http
			.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/**")
				.permitAll()
			.antMatchers("/**")
				.authenticated()
			.anyRequest()
				.permitAll();
		}

		@Override
		public void configure(final ResourceServerSecurityConfigurer resources) throws Exception {
			resources.tokenServices(tokenServices).resourceId("82dd7eb8-b2b9-4664-83cd-f7815d0651dd");
		}
	}

	@Value("${app.oauth2.sigingkey}")
	private String sigingKey;

	@Bean
	@Primary
	public JwtAccessTokenConverter accessTokenConverter() {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

		converter.setSigningKey(sigingKey);

		final OAuth2AccessTokenConverter accessTokenConverter = new OAuth2AccessTokenConverter();
		final DefaultUserAuthenticationConverter userTokenConverter = new DefaultUserAuthenticationConverter();
		accessTokenConverter.setUserTokenConverter(userTokenConverter);

		converter.setAccessTokenConverter(accessTokenConverter);

		return converter;
	}

	@Bean
	public DefaultTokenServices tokenServices() {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		return defaultTokenServices;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
}