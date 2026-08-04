package com.propflow.user.infrastructure.entrypoint.web.tenant.request;

import com.propflow.user.domain.model.vo.UserId;
import com.propflow.user.domain.port.in.CreateTenantCommand;
import com.propflow.user.infrastructure.entrypoint.web.request.DocumentTypeRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.apache.kafka.common.errors.InvalidRequestException;

public record CreateTenantRequest(
        @NotNull(message = "El tipo de documento es obligatorio")
        DocumentTypeRequest documentType,

        @NotBlank(message = "El número de documento es obligatorio")
        @Pattern(
                regexp = "^[0-9A-Za-z\\-]{5,20}$",
                message = "El número de documento solo puede contener letras, números y guiones (5-20 caracteres)"
        )
        String documentNumber,

        String status,

        Boolean hasCoDebtor,

        String advisorId,

        @NotNull(message = "Los datos bancarios son obligatorios")
        @Valid
        TenantCoDebtorRequest tenantCoDebtorRequest,

        @NotNull(message = "Los datos bancarios son obligatorios")
        @Valid
        TenantReferenceRequest tenantReferenceRequest
) {

    public CreateTenantRequest {
        if (documentType == DocumentTypeRequest.NIT && documentNumber != null) {
            if (!documentNumber.matches("^[0-9]{9,10}-[0-9]$")) {
                throw new InvalidRequestException(
                        "El NIT debe tener formato 123456789-0"
                );
            }
        }
    }

    public CreateTenantCommand toCommand(UserId userId) {
        return new CreateLandlordCommand(
                userId,
                documentType().toDomain(),
                documentNumber().trim(),

        );
    }
}
