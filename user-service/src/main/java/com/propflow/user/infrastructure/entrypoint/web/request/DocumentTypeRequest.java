package com.propflow.user.infrastructure.entrypoint.web.request;

import com.propflow.user.domain.model.vo.DocumentType;

public enum DocumentTypeRequest {
    CC,
    NIT,
    CE,
    PP;

    // Conversión al enum del dominio — el adapter traduce, el dominio no conoce este enum
    public DocumentType toDomain() {
        return DocumentType.valueOf(this.name());
    }
}
