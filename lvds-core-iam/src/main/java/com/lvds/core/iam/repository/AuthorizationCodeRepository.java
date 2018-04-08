package com.lvds.core.iam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lvds.core.iam.entity.AuthorizationCode;

public interface AuthorizationCodeRepository extends JpaRepository<AuthorizationCode, String>{

}
