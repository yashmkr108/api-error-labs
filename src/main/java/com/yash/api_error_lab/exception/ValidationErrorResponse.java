package com.yash.api_error_lab.exception;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record ValidationErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, List<String>> fieldErrors,
        List<String> globalErrors
) {
}