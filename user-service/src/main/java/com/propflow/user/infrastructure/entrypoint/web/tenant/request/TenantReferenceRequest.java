package com.propflow.user.infrastructure.entrypoint.web.tenant.request;

import com.propflow.user.domain.model.vo.TenantReference;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record TenantReferenceRequest(

        @NotNull(message = "El tipo de referencia es obligatorio")
        ReferenceTypeRequest referenceType,

        String name,
        String relationship,

        @NotBlank(message = "El teléfono de la referencia es obligatorio")
        String phone,

        String company,
        String position

) {
        public TenantReferenceRequest {
                if (referenceType == ReferenceTypeRequest.PERSONAL) {
                        if (name == null || name.isBlank())
                                throw new IllegalArgumentException(
                                        "El nombre es obligatorio para referencias personales");
                        if (relationship == null || relationship.isBlank())
                                throw new IllegalArgumentException(
                                        "La relación es obligatoria para referencias personales");
                }

                if (referenceType == ReferenceTypeRequest.LABORAL) {
                        if (company == null || company.isBlank())
                                throw new IllegalArgumentException(
                                        "La empresa es obligatoria para referencias laborales");
                        if (position == null || position.isBlank())
                                throw new IllegalArgumentException(
                                        "El cargo es obligatorio para referencias laborales");
                }
        }

        public TenantReference toDomain() {
                return switch (referenceType) {
                        case PERSONAL -> TenantReference.personal(name, phone, relationship);
                        case LABORAL  -> TenantReference.laboral(company, phone, position);
                };
        }
}
