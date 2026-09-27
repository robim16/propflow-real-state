package com.propflow.user.infrastructure.entrypoint.web.tenant.request;

import com.propflow.user.infrastructure.entrypoint.web.request.DocumentTypeRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record UpdateTenantRequest(
        @NotNull(message = "El tipo de documento es obligatorio")
        DocumentTypeRequest documentType,

        @NotBlank(message = "El número de documento es obligatorio")
        @Pattern(
                regexp = "^[0-9A-Za-z\\-]{5,20}$",
                message = "El número de documento solo puede contener letras, números y guiones (5-20 caracteres)"
        )
        String documentNumber,


        @NotEmpty(message = "Se requiere al menos una referencia")
        @Valid
        List<TenantReferenceRequest> references,

        @Valid
        TenantCoDebtorRequest coDebtor        // null si no tiene codeudor
) {
}
