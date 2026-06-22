package com.propflow.user.infrastructure.adapter.persistence.mapper;

import com.propflow.user.domain.model.Landlord;
import com.propflow.user.domain.model.vo.*;
import com.propflow.user.infrastructure.adapter.persistence.LandlordEntity;
import org.springframework.stereotype.Component;

@Component
public class LandlordEntityMapper {

    public LandlordEntity toEntity(Landlord domain) {
        var entity = new LandlordEntity();

        entity.setId(domain.getId().value());
        entity.setUserId(domain.getUserId().value());
        entity.setDocumentType(domain.getDocumentType().name());
        entity.setDocumentNumber(domain.getDocumentNumber());
        entity.setAddress(domain.getAddress());

        var bankAccount = domain.getBankAccount();
        entity.setBank(bankAccount.bank());
        entity.setBankAccountType(bankAccount.accountType().name());
        entity.setEncryptedAccountNumber(bankAccount.encryptedAccountNumber());
        entity.setAccountNumberHash(bankAccount.accountNumberHash());
        entity.setBankAccountLast4(bankAccount.last4());

        entity.setStatus(domain.getStatus().name());
        entity.setAdvisorId(domain.getAdvisorId());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());

        return entity;
    }

    public Landlord toDomain(LandlordEntity entity) {
        var bankAccount = BankAccount.of(
                entity.getBank(),
                BankAccountType.valueOf(entity.getBankAccountType()),
                entity.getEncryptedAccountNumber(),
                entity.getAccountNumberHash(),
                entity.getBankAccountLast4()
        );

        return Landlord.reconstitute(
                LandlordId.of(entity.getId()),
                UserId.of(entity.getUserId()),
                DocumentType.valueOf(entity.getDocumentType()),
                entity.getDocumentNumber(),
                entity.getAddress(),
                bankAccount,
                ProfileStatus.valueOf(entity.getStatus()),
                entity.getAdvisorId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}