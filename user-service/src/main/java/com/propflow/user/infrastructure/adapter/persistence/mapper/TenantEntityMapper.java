package com.propflow.user.infrastructure.adapter.persistence.mapper;

import com.propflow.user.domain.model.Tenant;
import com.propflow.user.domain.model.vo.*;
import com.propflow.user.infrastructure.adapter.persistence.TenantCoDebtorEntity;
import com.propflow.user.infrastructure.adapter.persistence.TenantEntity;
import com.propflow.user.infrastructure.adapter.persistence.TenantReferenceEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TenantEntityMapper {

    public TenantEntity toEntity(Tenant tenant) {
        var entity = new TenantEntity();
        entity.setId(tenant.getId().value());
        entity.setUserId(tenant.getUserId().value());
        entity.setDocumentType(tenant.getDocumentType().name());
        entity.setDocumentNumber(tenant.getDocumentNumber());
        entity.setStatus(tenant.getStatus().name());
        entity.setHasCoDebtor(tenant.hasCoDebtor());
        entity.setAdvisorId(tenant.getAdvisorId());
        entity.setCreatedAt(tenant.getCreatedAt());
        entity.setUpdatedAt(tenant.getUpdatedAt());
        return entity;
    }

    // ── TenantEntity + referencias + codeudor → Tenant ───────────────────
    // Recibe los tres orígenes de datos para reconstruir el agregado completo
    public Tenant toDomain(
            TenantEntity              entity,
            List<TenantReferenceEntity> referenceEntities,
            TenantCoDebtorEntity coDebtorEntity) {

        return Tenant.reconstitute(
                TenantId.of(entity.getId()),
                UserId.of(entity.getUserId()),
                DocumentType.valueOf(entity.getDocumentType()),
                entity.getDocumentNumber(),
                toReferencesDomain(referenceEntities),
                toCoDebtorDomain(coDebtorEntity),       // null si no hay codeudor
                ProfileStatus.valueOf(entity.getStatus()),
                entity.getAdvisorId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    // ── Tenant → List<TenantReferenceEntity> ─────────────────────────────
    public List<TenantReferenceEntity> toReferenceEntities(Tenant tenant) {
        return tenant.getReferences().stream()
                .map(ref -> {
                    var entity = new TenantReferenceEntity();
                    entity.setTenantId(tenant.getId().value());
                    entity.setReferenceType(ref.getType().name());
                    entity.setName(ref.getName());
                    entity.setPhone(ref.getPhone());
                    entity.setRelationship(ref.getRelationship());
                    entity.setCompany(ref.getCompany());
                    entity.setPosition(ref.getPosition());
                    return entity;
                })
                .toList();
    }

    // ── Tenant → TenantCoDebtorEntity ─────────────────────────────────────
    // Retorna null si el tenant no tiene codeudor
    public TenantCoDebtorEntity toCoDebtorEntity(Tenant tenant) {
        if (!tenant.hasCoDebtor()) {
            return null;
        }
        var coDebtor = tenant.getCoDebtor();
        var entity   = new TenantCoDebtorEntity();
        entity.setTenantId(tenant.getId().value());
        entity.setDocumentType(coDebtor.getDocumentType().name());
        entity.setDocumentNumber(coDebtor.getDocumentNumber());
        entity.setName(coDebtor.getName());
        entity.setPhone(coDebtor.getPhone());
        return entity;
    }

    // ── List<TenantReferenceEntity> → List<TenantReference> ──────────────
    private List<TenantReference> toReferencesDomain(
            List<TenantReferenceEntity> entities) {

        if (entities == null || entities.isEmpty()) {
            return List.of();
        }
        return entities.stream()
                .map(entity -> switch (
                        TenantReference.ReferenceType.valueOf(entity.getReferenceType())) {
                    case PERSONAL -> TenantReference.personal(
                            entity.getName(),
                            entity.getPhone(),
                            entity.getRelationship()
                    );
                    case LABORAL -> TenantReference.laboral(
                            entity.getCompany(),
                            entity.getPhone(),
                            entity.getPosition()
                    );
                })
                .toList();
    }

    // ── TenantCoDebtorEntity → CoDebtor ───────────────────────────────────
    // Retorna null si la entidad es null (tenant sin codeudor)
    private CoDebtor toCoDebtorDomain(TenantCoDebtorEntity entity) {
        if (entity == null) {
            return null;
        }
        return CoDebtor.of(
                DocumentType.valueOf(entity.getDocumentType()),
                entity.getDocumentNumber(),
                entity.getName(),
                entity.getPhone()
        );
    }
}
