package com.propflow.auth.infrastructure.entrypoint.web.dto;

public record TokenResponse(String accessToken, String refreshToken) {
}
