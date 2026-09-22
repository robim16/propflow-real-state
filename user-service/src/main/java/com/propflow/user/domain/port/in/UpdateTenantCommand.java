package com.propflow.user.domain.port.in;

import com.propflow.user.domain.model.vo.*;

import java.util.List;
import java.util.Objects;

public record UpdateTenantCommand(
       TenantId tenantId,
       UserPrincipal principal,
       DocumentType documentType,
       String       documentNumber,
       List<TenantReference> references,
       CoDebtor coDebtor
) {
    public UpdateTenantCommand {
        Objects.requireNonNull(tenantId,            "tenantId es obligatorio");
        Objects.requireNonNull(documentType,      "documentType es obligatorio");
        Objects.requireNonNull(documentNumber,    "documentNumber es obligatorio");
        Objects.requireNonNull(references,   "el listado de referencias es obligatorio");
        Objects.requireNonNull(coDebtor, "el codeudor es obligatorio");

        if (documentNumber.isBlank()) throw new IllegalArgumentException("documentNumber vacío");
    }
}
