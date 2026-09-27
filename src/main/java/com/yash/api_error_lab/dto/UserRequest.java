package com.yash.api_error_lab.dto;

import com.yash.api_error_lab.validation.annotation.PasswordsMatch;
import com.yash.api_error_lab.validation.group.CreateGroup;
import com.yash.api_error_lab.validation.group.UpdateGroup;
import jakarta.validation.constraints.*;

@PasswordsMatch(groups = {CreateGroup.class, UpdateGroup.class})
public record UserRequest(

        @NotBlank
        @Size(min = 4, max = 155)
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(max = 20)
        @Pattern(regexp = "^[0-9]+$", message = "Phone must contain only digits")
        String phone,

        @Max(120)
        @PositiveOrZero
        Integer age,

        @NotBlank
        String password,

        @NotBlank
        String confirmPassword

) {
}
