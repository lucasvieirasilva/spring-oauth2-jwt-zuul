package com.lvds.core.iam.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lvds.core.iam.entity.CustomUserDetails;
import com.lvds.core.iam.entity.User;
import com.lvds.core.iam.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	private CustomUserDetails buildUserDetails(final User user) {
		final List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("USER"));

		final String password = user.getPasswordHash() != null ? user.getPasswordHash() : "notsetpassowrd";

		final String displayName = String.format("%s %s", user.getFirstName(), user.getLastName());

		final CustomUserDetails userDetails = new CustomUserDetails(displayName, user.getId(), user.getUserName(), password,
				true, true, true, true, authorities, user.getProviderId(), user.getProviderKey(),
				user.getProviderAccessToken());
		return userDetails;
	}

	@Override
	public User loadUserByConnectionKey(final ConnectionKey connectionKey) {
		return userRepository
				.findByProviderIdAndProviderKey(connectionKey.getProviderId(), connectionKey.getProviderUserId())
				.orElseThrow(() -> new UsernameNotFoundException("O usuário não está cadastrado!"));
	}

	@Override
	public SocialUserDetails loadUserByUserId(final String userId) throws UsernameNotFoundException {
		return userRepository.findById(userId).map(user -> {
			final CustomUserDetails userDetails = buildUserDetails(user);

			return userDetails;
		}).orElseThrow(() -> new UsernameNotFoundException("O usuário não está cadastrado!"));
	}

	@Override
	public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
		return userRepository.findByUserNameAndActiveTrue(userName).map(user -> {

			final CustomUserDetails userDetails = buildUserDetails(user);

			return userDetails;
		}).orElseThrow(() -> new UsernameNotFoundException("O usuário não está cadastrado!"));
	}

	@Override
	public UserDetails loadUserDetailsByUserId(final String userId) {
		return userRepository.findById(userId).map(user -> {
			final CustomUserDetails userDetails = buildUserDetails(user);

			return userDetails;
		}).orElseThrow(() -> new UsernameNotFoundException("O usuário não está cadastrado!"));
	}

	@Override
	public void updateUser(final User user) {
		userRepository.save(user);
	}

}
