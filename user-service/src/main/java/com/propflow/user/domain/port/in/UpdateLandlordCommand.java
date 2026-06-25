package com.propflow.user.domain.port.in;

import com.propflow.user.domain.model.vo.BankAccountType;
import com.propflow.user.domain.model.vo.DocumentType;
import com.propflow.user.domain.model.vo.UserId;

import java.util.Objects;

public record UpdateLandlordCommand(
        UserId userId,
        DocumentType documentType,
        String         documentNumber,
        String         address,
        String         bank,
        BankAccountType bankAccountType,
        String         bankAccountNumber
) {
    public UpdateLandlordCommand {
        Objects.requireNonNull(userId,            "userId es obligatorio");
        Objects.requireNonNull(documentType,      "documentType es obligatorio");
        Objects.requireNonNull(documentNumber,    "documentNumber es obligatorio");
        Objects.requireNonNull(address,           "address es obligatorio");
        Objects.requireNonNull(bank,              "bank es obligatorio");
        Objects.requireNonNull(bankAccountType,   "bankAccountType es obligatorio");
        Objects.requireNonNull(bankAccountNumber, "bankAccountNumber es obligatorio");

        if (documentNumber.isBlank()) throw new IllegalArgumentException("documentNumber vacío");
        if (address.isBlank())        throw new IllegalArgumentException("address vacío");
    }
}
