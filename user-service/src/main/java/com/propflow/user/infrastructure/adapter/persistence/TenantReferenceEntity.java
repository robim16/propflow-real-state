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
@Table("tenant_references")
public class TenantReferenceEntity {
    @Id
    private String id;

    @Column("tenant_id")
    private String tenantId;

    @Column("reference_type")
    private String referenceType;   // PERSONAL | LABORAL

    private String name;
    private String phone;
    private String relationship;
    private String company;
    private String position;
}
