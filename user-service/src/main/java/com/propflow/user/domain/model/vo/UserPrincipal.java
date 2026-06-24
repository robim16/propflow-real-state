package com.propflow.user.domain.model.vo;


public record UserPrincipal(
        UserId   userId,
        Role     role,
        String   advisorId,   // solo presente si role == ADVISOR
        String   landlordId,  // solo presente si role == LANDLORD
        String   tenantId     // solo presente si role == TENANT
) {
    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    public boolean isAdvisor() {
        return role == Role.ADVISOR;
    }

    public boolean ownsLandlordProfile(String landlordIdToCheck) {
        return role == Role.LANDLORD
                && landlordId != null
                && landlordId.equals(landlordIdToCheck);
    }

    public boolean ownsTenantProfile(String tenantIdToCheck) {
        return role == Role.TENANT
                && tenantId != null
                && tenantId.equals(tenantIdToCheck);
    }
}
