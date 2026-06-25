package com.propflow.user.infrastructure.entrypoint.web.landlord.request;

import com.propflow.user.domain.model.vo.UserId;
import com.propflow.user.domain.port.in.CreateLandlordCommand;
import com.propflow.user.domain.port.in.UpdateLandlordCommand;
import com.propflow.user.infrastructure.entrypoint.web.dto.BankAccountRequest;
import com.propflow.user.infrastructure.entrypoint.web.request.DocumentTypeRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.apache.kafka.common.errors.InvalidRequestException;

public record UpdateLandlordRequest(

        @NotNull(message = "El tipo de documento es obligatorio")
        DocumentTypeRequest documentType,

        @NotBlank(message = "El número de documento es obligatorio")
        @Pattern(
                regexp = "^[0-9A-Za-z\\-]{5,20}$",
                message = "El número de documento solo puede contener letras, números y guiones (5-20 caracteres)"
        )
        String documentNumber,

        @NotBlank(message = "La dirección es obligatoria")
        @Size(min = 10, max = 200, message = "La dirección debe tener entre 10 y 200 caracteres")
        String address,

        @NotNull(message = "Los datos bancarios son obligatorios")
        @Valid
        BankAccountRequest bankAccount
) {

    public UpdateLandlordRequest {
        if (documentType == DocumentTypeRequest.NIT && documentNumber != null) {
            if (!documentNumber.matches("^[0-9]{9,10}-[0-9]$")) {
                throw new InvalidRequestException(
                        "El NIT debe tener formato 123456789-0"
                );
            }
        }
    }

    public UpdateLandlordCommand toCommand(UserId userId) {
        return new UpdateLandlordCommand(
                userId,
                documentType().toDomain(),
                documentNumber().trim(),
                address().trim(),
                bankAccount().bank().trim(),
                bankAccount().accountType().toDomain(),
                bankAccount().accountNumber()
        );
    }
}
