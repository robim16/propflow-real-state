package com.propflow.user.infrastructure.entrypoint.web.shared.response;

public record ErrorResponse(
        String type,
        String message
) {
    public static ErrorResponse of(String type, String message){
        return new ErrorResponse(type, message);
    }
}
