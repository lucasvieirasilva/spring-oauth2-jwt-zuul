package com.lvds.core.iam.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.security.SocialUserDetailsService;

import com.lvds.core.iam.entity.User;

public interface UserService extends UserDetailsService, SocialUserDetailsService {

	User loadUserByConnectionKey(ConnectionKey connectionKey);

	UserDetails loadUserDetailsByUserId(String userId);

	void updateUser(User user);
}
