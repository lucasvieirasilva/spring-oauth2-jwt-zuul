package com.lvds.core.iam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lvds.core.iam.entity.ScopeApproval;
import com.lvds.core.iam.entity.ScopeApprovalPK;

public interface ScopeApprovalRepository extends JpaRepository<ScopeApproval, ScopeApprovalPK> {

	List<ScopeApproval> findByUserIdAndClientId(String userId, String clientId);
}
