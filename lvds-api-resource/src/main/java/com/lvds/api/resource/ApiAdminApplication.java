package com.lvds.api.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.lvds.api.resource", "com.lvds.core.iam", "com.lvds.core.entity" })
public class ApiAdminApplication {

	public static void main(final String[] args) {
		SpringApplication.run(ApiAdminApplication.class, args);
	}
}
