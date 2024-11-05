package com.cleanarchitecture.presentation.api.rest.response;

import lombok.Value;
import java.time.LocalDateTime;
import java.util.List;

@Value
public class ErrorResponse {
    String message;
    List<String> errors;
    LocalDateTime timestamp;
    String path;

    public static ErrorResponse of(String message, List<String> errors, String path) {
        return new ErrorResponse(message, errors, LocalDateTime.now(), path);
    }

    public static ErrorResponse of(String message, String path) {
        return new ErrorResponse(message, List.of(), LocalDateTime.now(), path);
    }
}
