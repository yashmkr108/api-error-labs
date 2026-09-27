package com.yash.api_error_lab.dto;

import com.yash.api_error_lab.enums.UserStatus;

import java.time.Instant;

public record UserResponse(
        Long id,
        String name,
        String email,
        UserStatus status,
        Instant createdAt,
        Instant updatedAt
)
{
}
