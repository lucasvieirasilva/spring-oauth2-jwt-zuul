package com.lvds.auth.config.oauth2;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.lvds.core.iam.oauth2.OAuth2AccessTokenConverter;
import com.lvds.core.iam.repository.AuthorizationCodeRepository;
import com.lvds.core.iam.repository.ScopeApprovalRepository;
import com.lvds.core.iam.service.ClientService;
import com.lvds.core.iam.service.UserService;

@Configuration
public class OAuth2ServerConfig {

	protected static class ApprovalStoreConfig {

		@Autowired
		private ClientDetailsService clientDetailsService;

		@Autowired
		private ScopeApprovalRepository scopeApprovalRepository;

		@Bean
		public ApprovalStore approvalStore() throws Exception {
			final JpaApprovalStore store = new JpaApprovalStore(scopeApprovalRepository);
			return store;
		}

		@Bean
		@Lazy
		@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
		public UserApprovalHandler userApprovalHandler() throws Exception {
			final UserApprovalHandler handler = new UserApprovalHandler();
			handler.setApprovalStore(approvalStore());
			handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
			handler.setClientDetailsService(clientDetailsService);
			handler.setUseApprovalStore(true);
			return handler;
		}
	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
		@Autowired
		private AuthenticationManager authenticationManager;

		@Autowired
		private ClientService clientService;

		@Autowired
		private UserService userService;

		@Autowired
		private PasswordEncoder encoder;

		@Autowired
		private UserApprovalHandler userApprovalHandler;

		@Autowired
		private AuthorizationCodeRepository authorizationCodeStoreRepository;

		@Value("${app.oauth2.sigingkey}")
		private String sigingKey;

		@Bean
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
		public AuthorizationCodeServices authorizationCodeService() {
			return new JpaAuthorizationCodeServices(authorizationCodeStoreRepository);
		}

		@Override
		public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.setClientDetailsService(clientService);
			endpoints.tokenStore(tokenStore()).authorizationCodeServices(authorizationCodeService())
					.userApprovalHandler(userApprovalHandler).tokenEnhancer(tokenEnhancerChain())
					.userDetailsService(userService).authenticationManager(authenticationManager);
		}

		@Override
		public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
			oauthServer.passwordEncoder(encoder);
			oauthServer.allowFormAuthenticationForClients();
		}

		@Override
		public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
			clients.withClientDetails(clientService);
		}

		@Bean
		public TokenEnhancer tokenEnhancer() {
			return new OAuth2TokenEnhancer();
		}

		@Bean
		public TokenEnhancerChain tokenEnhancerChain() {
			final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
			tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
			return tokenEnhancerChain;
		}

		@Bean
		public TokenStore tokenStore() {
			return new JwtTokenStore(accessTokenConverter());
		}
	}
}
