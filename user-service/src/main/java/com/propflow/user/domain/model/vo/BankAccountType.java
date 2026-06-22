package com.propflow.user.domain.model.vo;

public enum BankAccountType {

    SAVINGS("Cuenta de ahorros"),
    CHECKING("Cuenta corriente");

    private final String displayName;

    BankAccountType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}