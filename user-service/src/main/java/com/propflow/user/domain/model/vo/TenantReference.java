package com.propflow.user.domain.model.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TenantReference {

    private final ReferenceType type;
    private final String        name;         // para referencias personales
    private final String        phone;
    private final String        relationship; // para referencias personales
    private final String        company;      // para referencias laborales
    private final String        position;     // para referencias laborales


    // ── Factory method: referencia personal ──────────────────────────────
    public static TenantReference personal(
            String name,
            String phone,
            String relationship) {

        if (name == null || name.isBlank())
            throw new IllegalArgumentException(
                    "El nombre de la referencia personal es obligatorio");
        if (phone == null || phone.isBlank())
            throw new IllegalArgumentException(
                    "El teléfono de la referencia personal es obligatorio");
        if (relationship == null || relationship.isBlank())
            throw new IllegalArgumentException(
                    "La relación de la referencia personal es obligatoria");

        return new TenantReference(
                ReferenceType.PERSONAL,
                name.trim(),
                phone.trim(),
                relationship.trim(),
                null,
                null
        );
    }

    // ── Factory method: referencia laboral ───────────────────────────────
    public static TenantReference laboral(
            String company,
            String phone,
            String position) {

        if (company == null || company.isBlank())
            throw new IllegalArgumentException(
                    "La empresa de la referencia laboral es obligatoria");
        if (phone == null || phone.isBlank())
            throw new IllegalArgumentException(
                    "El teléfono de la referencia laboral es obligatorio");
        if (position == null || position.isBlank())
            throw new IllegalArgumentException(
                    "El cargo de la referencia laboral es obligatorio");

        return new TenantReference(
                ReferenceType.LABORAL,
                null,
                phone.trim(),
                null,
                company.trim(),
                position.trim()
        );
    }
}
