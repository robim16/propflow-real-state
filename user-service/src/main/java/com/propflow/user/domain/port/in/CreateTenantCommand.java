package com.propflow.user.domain.port.in;

import com.propflow.user.domain.model.vo.*;

import java.util.List;
import java.util.Objects;

public record CreateTenantCommand(
        UserId                userId,
        DocumentType          documentType,
        String                documentNumber,
        List<TenantReference> references,
        CoDebtor              coDebtor        // null si no tiene codeudor
) {
    public CreateTenantCommand {
        // Obligatorios
        Objects.requireNonNull(userId,         "userId es obligatorio");
        Objects.requireNonNull(documentType,   "documentType es obligatorio");
        Objects.requireNonNull(references,     "la lista de referencias es obligatoria");

        if (documentNumber == null || documentNumber.isBlank())
            throw new IllegalArgumentException("documentNumber es obligatorio");

        // Referencias — mínimo una personal y una laboral
        boolean tienePersonal = references.stream()
                .anyMatch(r -> r.getType() == ReferenceType.PERSONAL);
        boolean tieneLaboral = references.stream()
                .anyMatch(r -> r.getType() == ReferenceType.LABORAL);

        if (!tienePersonal)
            throw new IllegalArgumentException(
                    "Se requiere al menos una referencia personal");
        if (!tieneLaboral)
            throw new IllegalArgumentException(
                    "Se requiere al menos una referencia laboral");

        // coDebtor es null si el tenant no tiene codeudor — no se valida
        documentNumber = documentNumber.trim();
    }
}