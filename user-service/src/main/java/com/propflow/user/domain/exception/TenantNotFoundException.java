package com.propflow.user.domain.exception;

import com.propflow.user.domain.model.vo.TenantId;

public class TenantNotFoundException extends RuntimeException {
    public TenantNotFoundException(TenantId tenantId) {
        super("tenant with id " + String.valueOf(tenantId) + " not found");
    }
}
