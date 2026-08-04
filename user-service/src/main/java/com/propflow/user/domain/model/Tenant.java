package com.propflow.user.domain.model;

import com.propflow.user.domain.model.event.DomainEvent;
import com.propflow.user.domain.model.event.TenantCreatedEvent;
import com.propflow.user.domain.model.vo.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Tenant {

    private TenantId              id;
    private UserId                userId;
    private DocumentType          documentType;
    private String                documentNumber;
    private List<TenantReference> references;
    private CoDebtor              coDebtor;
    private ProfileStatus         status;
    private String                advisorId;
    private Instant               createdAt;
    private Instant               updatedAt;

    @Getter(AccessLevel.NONE)
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    // ── Factory method: crear nuevo arrendatario ──────────────────────────
    public static Tenant create(
            UserId                userId,
            DocumentType          documentType,
            String                documentNumber,
            List<TenantReference> references,
            CoDebtor              coDebtor) {

        validateDocumentNumber(documentNumber);
        validateReferences(references);

        var now    = Instant.now();
        var tenant = new Tenant();

        tenant.id             = TenantId.generate();
        tenant.userId         = userId;
        tenant.documentType   = documentType;
        tenant.documentNumber = documentNumber;
        tenant.references     = new ArrayList<>(references);
        tenant.coDebtor       = coDebtor;
        tenant.createdAt      = now;
        tenant.updatedAt      = now;
        tenant.status         = tenant.computeStatus();

        tenant.domainEvents.add(new TenantCreatedEvent(
                tenant.id.value(),
                tenant.userId.value(),
                tenant.status,
                now
        ));

        return tenant;
    }

    // ── Factory method: reconstruir desde persistencia ────────────────────
    public static Tenant reconstitute(
            TenantId              id,
            UserId                userId,
            DocumentType          documentType,
            String                documentNumber,
            List<TenantReference> references,
            CoDebtor              coDebtor,
            ProfileStatus         status,
            String                advisorId,
            Instant               createdAt,
            Instant               updatedAt) {

        var tenant = new Tenant();

        tenant.id             = id;
        tenant.userId         = userId;
        tenant.documentType   = documentType;
        tenant.documentNumber = documentNumber;
        tenant.references     = new ArrayList<>(references != null ? references : List.of());
        tenant.coDebtor       = coDebtor;
        tenant.status         = status;
        tenant.advisorId      = advisorId;
        tenant.createdAt      = createdAt;
        tenant.updatedAt      = updatedAt;

        return tenant;
    }

    // ── Reglas de negocio ─────────────────────────────────────────────────

    public void updateReferences(List<TenantReference> newReferences) {
        validateReferences(newReferences);
        this.references = new ArrayList<>(newReferences);
        this.updatedAt  = Instant.now();
        this.status     = computeStatus();
    }

    public void updateCoDebtor(CoDebtor newCoDebtor) {
        this.coDebtor  = newCoDebtor;
        this.updatedAt = Instant.now();
    }

    public void removeCoDebtor() {
        this.coDebtor  = null;
        this.updatedAt = Instant.now();
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

    public void markProfileComplete() {
        this.status    = ProfileStatus.COMPLETE;
        this.updatedAt = Instant.now();
    }

    public boolean hasCoDebtor() {
        return this.coDebtor != null;
    }

    public boolean isComplete() {
        return this.status == ProfileStatus.COMPLETE;
    }

    // Evita exponer la lista interna directamente
    public List<TenantReference> getReferences() {
        return Collections.unmodifiableList(references);
    }

    // ── Eventos de dominio ────────────────────────────────────────────────
    public List<DomainEvent> pullDomainEvents() {
        var events = List.copyOf(domainEvents);
        domainEvents.clear();
        return events;
    }

    // ── Cálculo de estado ─────────────────────────────────────────────────
    private ProfileStatus computeStatus() {
        boolean tieneDocumento = documentType != null
                && documentNumber != null && !documentNumber.isBlank();

        boolean tienePersonal = references != null && references.stream()
                .anyMatch(r -> r.type() == TenantReference.ReferenceType.PERSONAL);

        boolean tieneLaboral = references != null && references.stream()
                .anyMatch(r -> r.type() == TenantReference.ReferenceType.LABORAL);

        return tieneDocumento && tienePersonal && tieneLaboral
                ? ProfileStatus.COMPLETE
                : ProfileStatus.INCOMPLETE;
    }

    // ── Validaciones estáticas ────────────────────────────────────────────
    private static void validateDocumentNumber(String documentNumber) {
        if (documentNumber == null || documentNumber.isBlank())
            throw new IllegalArgumentException("El número de documento es obligatorio");
    }

    private static void validateReferences(List<TenantReference> references) {
        if (references == null || references.isEmpty())
            throw new IllegalArgumentException(
                    "Se requiere al menos una referencia personal y una laboral");

        boolean tienePersonal = references.stream()
                .anyMatch(r -> r.type() == TenantReference.ReferenceType.PERSONAL);
        boolean tieneLaboral  = references.stream()
                .anyMatch(r -> r.type() == TenantReference.ReferenceType.LABORAL);

        if (!tienePersonal)
            throw new IllegalArgumentException("Se requiere al menos una referencia personal");
        if (!tieneLaboral)
            throw new IllegalArgumentException("Se requiere al menos una referencia laboral");
    }
}
