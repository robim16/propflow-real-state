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


    public TenantId      getId()             { return id;             }
    public UserId        getUserId()         { return userId;         }
    public DocumentType  getDocumentType()   { return documentType;   }
    public String        getDocumentNumber() { return documentNumber; }
    public String        getAddress()        { return address;        }
    public BankAccount   getBankAccount()    { return bankAccount;    }
    public ProfileStatus getStatus()         { return status;         }
    public String        getAdvisorId()      { return advisorId;      }
    public Instant       getCreatedAt()      { return createdAt;      }
    public Instant       getUpdatedAt()      { return updatedAt;      }
}
