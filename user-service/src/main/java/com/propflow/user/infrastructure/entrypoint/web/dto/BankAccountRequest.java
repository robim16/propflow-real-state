package com.propflow.user.infrastructure.entrypoint.web.dto;

import com.propflow.user.infrastructure.entrypoint.web.request.BankAccountTypeRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BankAccountRequest(
        @NotBlank String bank,
        //@NotBlank String accountType,        // SAVINGS | CHECKING
        @NotNull(message = "El tipo de cuenta es obligatorio")
        @Valid
        BankAccountTypeRequest accountType,

        @NotBlank String accountNumber
) {
}
