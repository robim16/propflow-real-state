package com.propflow.user.infrastructure.entrypoint.web.shared.response;

public record ErrorResponse(
        String type,
        String message
) {
    public static ErrorResponse from(String type, String message){
        return new ErrorResponse(type, message);
    }
}
