package com.propflow.user.domain.model.vo;

import com.propflow.user.domain.exception.InvalidBankAccountException;

public record BankAccount(
        String          bank,
        BankAccountType accountType,
        String          encryptedAccountNumber,
        String          accountNumberHash,
        String          last4
) {
    public static BankAccount of(
            String bank, BankAccountType type,
            String encryptedAccountNumber, String accountNumberHash, String last4) {

        if (encryptedAccountNumber == null || encryptedAccountNumber.isBlank())
            throw new InvalidBankAccountException("Número de cuenta cifrado inválido");
        if (accountNumberHash == null || accountNumberHash.isBlank())
            throw new InvalidBankAccountException("Hash de cuenta inválido");
        if (last4 == null || last4.length() != 4)
            throw new InvalidBankAccountException("Los últimos 4 dígitos son inválidos");

        return new BankAccount(bank, type, encryptedAccountNumber, accountNumberHash, last4);
    }
}