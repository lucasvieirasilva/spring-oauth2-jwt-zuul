package com.lvds.core.iam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lvds.core.iam.entity.Client;

public interface ClientRepository extends JpaRepository<Client, String> {
	Optional<Client> findByIdAndActiveTrue(String id);
}
