package com.propflow.user.domain.exception;

import com.propflow.user.domain.model.vo.TenantId;
import com.propflow.user.domain.model.vo.UserId;

public class TenantNotFoundException extends RuntimeException {
    public TenantNotFoundException(TenantId tenantId) {
        super("tenant with id " + tenantId.value() + " not found");
    }

    public TenantNotFoundException(UserId userId) {
        super("tenant for user id " + userId.value() + " not found");
    }
}
