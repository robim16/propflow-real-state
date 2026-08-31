package com.propflow.user.infrastructure.entrypoint.web.tenant.request;

import com.propflow.user.domain.model.vo.DocumentType;
import com.propflow.user.infrastructure.entrypoint.web.request.DocumentTypeRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TenantCoDebtorRequest(
        @NotNull(message = "El tipo de documento es obligatorio")
        DocumentTypeRequest documentType,

        @NotBlank(message = "El número de documento es obligatorio")
        @Pattern(
                regexp = "^[0-9A-Za-z\\-]{5,20}$",
                message = "El número de documento solo puede contener letras, números y guiones (5-20 caracteres)"
        )
        String       documentNumber,

        @NotNull(message = "El nombre del codeudor es obligatorio")
        String       name,

        @NotNull(message = "El telefono del codeudor es obligatorio")
        String       phone
) {
}
