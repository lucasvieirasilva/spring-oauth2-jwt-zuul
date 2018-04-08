package com.lvds.auth.config.oauth2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;

import com.lvds.core.iam.entity.ScopeApproval;
import com.lvds.core.iam.entity.ScopeApprovalPK;
import com.lvds.core.iam.repository.ScopeApprovalRepository;

public class JpaApprovalStore implements ApprovalStore {

	private final ScopeApprovalRepository repository;

	public JpaApprovalStore(final ScopeApprovalRepository scopeApprovalRepository) {
		this.repository = scopeApprovalRepository;
	}

	@Override
	public boolean addApprovals(final Collection<Approval> approvals) {
		if (approvals != null) {
			for (final Approval approval : approvals) {
				final ScopeApproval entity = new ScopeApproval();

				entity.setClientId(approval.getClientId());
				entity.setUserId(approval.getUserId());
				entity.setScope(approval.getScope());
				entity.setExpiresAt(approval.getExpiresAt());
				entity.setLastUpdatedAt(approval.getLastUpdatedAt());
				entity.setStatus(approval.getStatus());

				repository.save(entity);
			}
		}

		return true;
	}

	@Override
	public Collection<Approval> getApprovals(final String userId, final String clientId) {
		final List<Approval> approvals = new ArrayList<>();

		final List<ScopeApproval> entities = repository.findByUserIdAndClientId(userId, clientId);

		for (final ScopeApproval entity : entities) {
			approvals.add(new Approval(entity.getUserId(), entity.getClientId(), entity.getScope(),
					entity.getExpiresAt(), entity.getStatus()));
		}

		return approvals;
	}

	@Override
	public boolean revokeApprovals(final Collection<Approval> approvals) {
		for (final Approval approval : approvals) {
			final ScopeApproval entity = repository
					.findOne(new ScopeApprovalPK(approval.getUserId(), approval.getClientId(), approval.getScope()));

			if (entity != null) {
				repository.delete(entity);
			}
		}

		return true;
	}

}
