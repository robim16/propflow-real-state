package com.propflow.user.domain.model;

import com.propflow.user.domain.model.vo.*;
import lombok.Builder;
import lombok.With;

import java.time.Instant;

@Builder
@With
public class Tenant {

    private final TenantId id;
    private final UserId userId;
    private DocumentType documentType;
    private       String          documentNumber;
    private       String          address;
    private BankAccount bankAccount;
    private ProfileStatus status;
    private       String          advisorId;
    private final Instant createdAt;
    private       Instant         updatedAt;
}
