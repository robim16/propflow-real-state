package com.propflow.user.infrastructure.entrypoint.web.request;

import com.propflow.user.domain.model.vo.BankAccountType;

public enum BankAccountTypeRequest {
    SAVINGS,
    CHECKING;

    public BankAccountType toDomain() {
        return BankAccountType.valueOf(this.name());
    }
}
