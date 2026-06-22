
package com.propflow.user.domain.model;

import com.propflow.user.domain.model.vo.DocumentType;
import com.propflow.user.domain.model.vo.LandlordId;
import com.propflow.user.domain.model.event.DomainEvent;
import com.propflow.user.domain.model.event.LandlordCreatedEvent;
import com.propflow.user.domain.model.vo.BankAccount;
import com.propflow.user.domain.model.vo.DocumentType;
import com.propflow.user.domain.model.vo.LandlordId;
import com.propflow.user.domain.model.vo.ProfileStatus;
import com.propflow.user.domain.model.vo.UserId;
import lombok.Builder;
import lombok.With;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Builder
@With
public class Landlord {

    private final LandlordId id;
    private final UserId          userId;
    private DocumentType documentType;
    private       String          documentNumber;
    private       String          address;
    private       BankAccount     bankAccount;
    private       ProfileStatus   status;
    private       String          advisorId;
    private final Instant         createdAt;
    private       Instant         updatedAt;

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    // ── Constructor privado ───────────────────────────────────────────────
    private Landlord(
            LandlordId   id,
            UserId       userId,
            DocumentType documentType,
            String       documentNumber,
            String       address,
            BankAccount  bankAccount,
            Instant      createdAt) {

        this.id             = id;
        this.userId         = userId;
        this.documentType   = documentType;
        this.documentNumber = documentNumber;
        this.address        = address;
        this.bankAccount    = bankAccount;
        this.createdAt      = createdAt;
        this.updatedAt      = createdAt;
        this.status         = computeStatus(documentType, documentNumber, address, bankAccount);
    }

    // ── Factory method: crear nuevo arrendador ────────────────────────────
    public static Landlord create(
            UserId       userId,
            DocumentType documentType,
            String       documentNumber,
            String       address,
            BankAccount  bankAccount) {

        var now = Instant.now();

        var landlord = new Landlord(
                LandlordId.generate(),
                userId,
                documentType,
                documentNumber,
                address,
                bankAccount,
                now
        );

        landlord.domainEvents.add(new LandlordCreatedEvent(
                landlord.id.value(),
                landlord.userId.value(),
                landlord.status,
                now
        ));

        return landlord;
    }

    // ── Factory method: reconstruir desde persistencia ────────────────────
    public static Landlord reconstitute(
            LandlordId   id,
            UserId       userId,
            DocumentType documentType,
            String       documentNumber,
            String       address,
            BankAccount  bankAccount,
            ProfileStatus status,
            String       advisorId,
            Instant      createdAt,
            Instant      updatedAt) {

        var landlord = new Landlord(
                id, userId, documentType,
                documentNumber, address, bankAccount, createdAt
        );
        landlord.status    = status;
        landlord.advisorId = advisorId;
        landlord.updatedAt = updatedAt;
        return landlord;
    }

    // ── Reglas de negocio ─────────────────────────────────────────────────

    public boolean canRegisterProperties() {
        return this.status == ProfileStatus.COMPLETE;
    }

    public void updateAddress(String newAddress) {
        if (newAddress == null || newAddress.isBlank())
            throw new IllegalArgumentException("La dirección no puede estar vacía");
        this.address   = newAddress.trim();
        this.updatedAt = Instant.now();
        this.status    = computeStatus(documentType, documentNumber, address, bankAccount);
    }

    public void updateBankAccount(BankAccount newBankAccount) {
        if (newBankAccount == null)
            throw new IllegalArgumentException("Los datos bancarios no pueden ser nulos");
        this.bankAccount = newBankAccount;
        this.updatedAt   = Instant.now();
        this.status      = computeStatus(documentType, documentNumber, address, bankAccount);
    }

    public void assignAdvisor(String advisorId) {
        if (advisorId == null || advisorId.isBlank())
            throw new IllegalArgumentException("El advisorId no puede estar vacío");
        this.advisorId = advisorId;
        this.updatedAt = Instant.now();
    }

    public void unassignAdvisor() {
        this.advisorId = null;
        this.updatedAt = Instant.now();
    }

    // ── Lógica de cálculo de estado ───────────────────────────────────────
    private static ProfileStatus computeStatus(
            DocumentType documentType,
            String       documentNumber,
            String       address,
            BankAccount  bankAccount) {

        boolean complete = documentType   != null
                && documentNumber != null && !documentNumber.isBlank()
                && address        != null && !address.isBlank()
                && bankAccount    != null;

        return complete ? ProfileStatus.COMPLETE : ProfileStatus.INCOMPLETE;
    }

    // ── Eventos de dominio ────────────────────────────────────────────────
    public List<DomainEvent> pullDomainEvents() {
        var events = List.copyOf(domainEvents);
        domainEvents.clear();
        return events;
    }

    // ── Getters ───────────────────────────────────────────────────────────
    public LandlordId    getId()             { return id;             }
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