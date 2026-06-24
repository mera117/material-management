package com.elizabeth.material_management.dto.response;

public record ApiResponse<T>(
        int code,
        String message,
        T data
) {
}
