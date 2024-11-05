package com.cleanarchitecture.presentation.api.rest.response;

import lombok.Value;
import java.time.LocalDateTime;

@Value
public class ApiResponse<T> {
    T data;
    String message;
    LocalDateTime timestamp;

    private ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, "Success");
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, message);
    }
}
