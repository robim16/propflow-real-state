package com.propflow.user.infrastructure.entrypoint.web.tenant.request;

import com.propflow.user.domain.model.vo.ReferenceType;

public enum ReferenceTypeRequest {

    PERSONAL,
    LABORAL;

    public ReferenceType toDomain() {
        return ReferenceType.valueOf(this.name());
    }
}
