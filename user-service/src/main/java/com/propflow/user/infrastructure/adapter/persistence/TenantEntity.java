package com.propflow.user.infrastructure.adapter.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tenants")
public class TenantEntity {
    @Id
    private String  id;

    @Column("user_id")
    private String  userId;

    @Column("document_type")
    private String  documentType;

    @Column("document_number")
    private String  documentNumber;

    @Column("status")
    private String  status;           // INCOMPLETE | COMPLETE

    @Column("has_co_debtor")
    private Boolean hasCoDebtor;

    @Column("advisor_id")
    private String  advisorId;

    @Column("created_at")
    private Instant createdAt;

    @Column("updated_at")
    private Instant updatedAt;

}
