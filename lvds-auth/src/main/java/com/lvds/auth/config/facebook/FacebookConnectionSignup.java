package com.lvds.auth.config.facebook;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;

import com.lvds.core.iam.entity.User;
import com.lvds.core.iam.enums.RoleIds;
import com.lvds.core.iam.repository.RoleRepository;
import com.lvds.core.iam.repository.UserRepository;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public String execute(final Connection<?> connection) {

		User user = null;

		if (connection.getApi() instanceof Facebook) {

			final Facebook facebook = (Facebook) connection.getApi();

			final String[] fields = { "id", "name", "first_name", "last_name", "email" };
			final org.springframework.social.facebook.api.User profile = facebook.fetchObject("me",
					org.springframework.social.facebook.api.User.class, fields);

			final Optional<User> result = userRepository.findByUserName(profile.getId());

			if (!result.isPresent()) {
				user = new User();
				user.setFirstName(profile.getFirstName());
				user.setLastName(profile.getLastName());
				user.setUserName(profile.getId());
				user.setEmail(profile.getEmail());
				user.setEmailConfirmated(true);
				user.setActive(true);
				setConnectionProvider(connection, user);
				user.setRoles(Arrays.asList(roleRepository.findOne(RoleIds.USERS.getId())));

				userRepository.save(user);
			} else {
				user = result.get();

				setConnectionProvider(connection, user);

				userRepository.save(user);
			}
		} else {
			throw new RuntimeException("the API connection for facebook is invalid");
		}

		return user.getUserName();
	}

	private void setConnectionProvider(final Connection<?> connection, final User user) {
		user.setProviderId(connection.getKey().getProviderId());
		user.setProviderKey(connection.getKey().getProviderUserId());
		user.setProviderAccessToken(connection.createData().getAccessToken());
	}
}