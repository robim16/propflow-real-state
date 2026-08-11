package com.propflow.user.infrastructure.entrypoint.web.tenant.request;

import com.propflow.user.domain.model.vo.CoDebtor;
import com.propflow.user.domain.model.vo.DocumentType;
import com.propflow.user.domain.model.vo.TenantReference;
import com.propflow.user.domain.model.vo.UserId;
import com.propflow.user.domain.port.in.CreateTenantCommand;
import com.propflow.user.infrastructure.entrypoint.web.request.DocumentTypeRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.apache.kafka.common.errors.InvalidRequestException;

import java.util.ArrayList;
import java.util.List;

public record CreateTenantRequest(
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
        List<TenantReference> domainReferences = references.stream()
                .map(TenantReferenceRequest::toDomain)
                .toList();

        // Convierte el codeudor al value object del dominio (null si no viene)
        CoDebtor domainCoDebtor = coDebtor != null
                ? CoDebtor.of(
                DocumentType.valueOf(coDebtor.documentType().name()),
                coDebtor.documentNumber(),
                coDebtor.name(),
                coDebtor.phone())
                : null;

        return new CreateTenantCommand(
                userId,
                DocumentType.valueOf(documentType.name()),
                documentNumber,
                domainReferences,
                domainCoDebtor
        );
    }
}
