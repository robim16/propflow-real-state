package com.propflow.user.infrastructure.adapter.persistence.mapper;

import com.propflow.user.domain.model.Advisor;
import com.propflow.user.domain.model.vo.AdvisorId;
import com.propflow.user.domain.model.vo.DocumentType;
import com.propflow.user.domain.model.vo.ProfileStatus;
import com.propflow.user.domain.model.vo.UserId;
import com.propflow.user.infrastructure.adapter.persistence.AdvisorEntity;

public class AdvisorEntityMapper {

    public AdvisorEntity toEntity(Advisor domain) {
        var entity = new AdvisorEntity();
        entity.setId(domain.getId().toString());
        entity.setUserId(domain.getUserId().value());
        entity.setDocumentType(domain.getDocumentType().name());
        entity.setDocumentNumber(domain.getDocumentNumber());
        entity.setStatus(domain.getStatus().name());

        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());

        return entity;
    }

    public Advisor toDomain(AdvisorEntity entity) {
        return Advisor.reconstitute(
                AdvisorId.of(entity.getId()),
                UserId.of(entity.getUserId()),
                DocumentType.valueOf(entity.getDocumentType()),
                entity.getDocumentNumber(),
                ProfileStatus.valueOf(entity.getStatus()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
