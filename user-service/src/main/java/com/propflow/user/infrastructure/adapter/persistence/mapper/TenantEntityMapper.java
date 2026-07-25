package com.propflow.user.infrastructure.adapter.persistence.mapper;

import com.propflow.user.domain.model.Tenant;
import com.propflow.user.infrastructure.adapter.persistence.TenantEntity;
import org.springframework.stereotype.Component;

@Component
public class TenantEntityMapper {

    public TenantEntity toEntity(Tenant domain) {
        var entity = new TenantEntity();
        entity.setId(domain.getId().value());
        entity.setUserId(domain.getUserId().value());
        entity.setDocumentType(domain.getDocumentType().toString());
        entity.setDocumentNumber(domain.getDocumentNumber());
        entity.setStatus(domain.getStatus().name());


    }
}
