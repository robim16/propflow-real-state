package com.propflow.user.infrastructure.adapter.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("landlords")
public class LandlordEntity {
    @Id
    private String  id;
    private String  userId;
    private String  documentType;
    private String  documentNumber;
    private String  address;
    private String  bank;
    private String  bankAccountType;
    private String  encryptedAccountNumber;
    private String  accountNumberHash;
    private String  bankAccountLast4;
    private String  status;
    private String  advisorId;
    private Instant createdAt;
    private Instant updatedAt;
}
