package com.lvds.core.iam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lvds.core.iam.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findById(String userId);

	Optional<User> findByProviderIdAndProviderKey(String providerId, String providerKey);

	Optional<User> findByUserName(String userName);

	Optional<User> findByUserNameAndActiveTrue(String userName);
}
