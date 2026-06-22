package com.propflow.auth.infrastructure.entrypoint.web.dto;

import com.propflow.auth.domain.model.UserRole;

import java.util.UUID;

public record UserResponse(UUID id, String name, String email, UserRole role) { }
