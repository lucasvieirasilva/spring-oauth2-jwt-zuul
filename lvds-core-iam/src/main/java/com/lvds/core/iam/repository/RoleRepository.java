package com.lvds.core.iam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lvds.core.iam.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

}
