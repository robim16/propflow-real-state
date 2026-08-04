package com.propflow.user.domain.model.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CoDebtor {

    private final DocumentType documentType;
    private final String       documentNumber;
    private final String       name;
    private final String       phone;

    // ── Factory method ────────────────────────────────────────────────────
    public static CoDebtor of(
            DocumentType documentType,
            String       documentNumber,
            String       name,
            String       phone) {

        if (documentType == null)
            throw new IllegalArgumentException(
                    "El tipo de documento del codeudor es obligatorio");
        if (documentNumber == null || documentNumber.isBlank())
            throw new IllegalArgumentException(
                    "El número de documento del codeudor es obligatorio");
        if (name == null || name.isBlank())
            throw new IllegalArgumentException(
                    "El nombre del codeudor es obligatorio");
        if (phone == null || phone.isBlank())
            throw new IllegalArgumentException(
                    "El teléfono del codeudor es obligatorio");

        return new CoDebtor(
                documentType,
                documentNumber.trim(),
                name.trim(),
                phone.trim()
        );
    }
}
