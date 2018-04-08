package com.lvds.auth;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.lvds.core.iam.entity.Client;
import com.lvds.core.iam.entity.Role;
import com.lvds.core.iam.entity.User;
import com.lvds.core.iam.enums.RoleIds;
import com.lvds.core.iam.repository.ClientRepository;
import com.lvds.core.iam.repository.RoleRepository;
import com.lvds.core.iam.repository.UserRepository;

@SpringBootApplication(scanBasePackages = { "com.lvds.auth", "com.lvds.core.iam", "com.lvds.core.entity" })
public class AuthApplication {

	public static void main(final String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Bean
	@Transactional
	public CommandLineRunner init(final RoleRepository roleRepo, final ClientRepository clientRepo,
			final UserRepository userRepo, final PasswordEncoder passwordEncoder) {
		return (args) -> {
			final Role adminRole = new Role();
			adminRole.setId(RoleIds.ADMINS.getId());
			adminRole.setName("Admins");

			roleRepo.save(adminRole);

			final Role facebookUser = new Role();
			facebookUser.setId(RoleIds.USERS.getId());
			facebookUser.setName("Users");

			roleRepo.save(facebookUser);

			final Client client = new Client();
			client.setId("24b1d5e9-cb4f-4f18-937f-6ae2bff31aa1");
			client.setName("LdvsClient");
			client.setClientSecret(passwordEncoder.encode("8462898e-ee33-4d3e-af8a-d45b9a956037"));
			client.setAccessTokenValiditySeconds(1800);
			client.setRefreshTokenValiditySeconds(432000);
			client.setGrantTypes(Arrays.asList("password", "authorization_code", "refresh_token"));
			client.setScopes(Arrays.asList("read", "write"));
			client.setAutoApprovedScopes(Arrays.asList("read", "write"));
			client.setResources(Arrays.asList("82dd7eb8-b2b9-4664-83cd-f7815d0651dd"));
			client.setRoles(Arrays.asList(adminRole));
			client.setActive(true);

			clientRepo.save(client);

			final User adminUser = new User();
			adminUser.setId("88c0fc53-ce26-4356-b0f4-f09005584865");
			adminUser.setFirstName("Admin");
			adminUser.setLastName("Admin");
			adminUser.setUserName("admin");
			adminUser.setPasswordHash(passwordEncoder.encode("admin"));
			adminUser.setEmail("admin@lvds.com");
			adminUser.setEmailConfirmated(true);
			adminUser.setRoles(Arrays.asList(adminRole));
			adminUser.setActive(true);

			userRepo.save(adminUser);
		};
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
