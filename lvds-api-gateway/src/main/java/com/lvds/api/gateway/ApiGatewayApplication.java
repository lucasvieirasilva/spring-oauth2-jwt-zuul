package com.lvds.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableOAuth2Sso
@EnableZuulProxy
public class ApiGatewayApplication {

	public static void main(final String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
}
