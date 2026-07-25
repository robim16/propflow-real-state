package com.propflow.user.infrastructure.adapter.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tenant_co_debtors")
public class TenantCoDebtorEntity {
    @Id
    private String id;

    @Column("tenant_id")
    private String tenantId;

    @Column("document_type")
    private String documentType;

    @Column("document_number")
    private String documentNumber;

    private String name;
    private String phone;
}
