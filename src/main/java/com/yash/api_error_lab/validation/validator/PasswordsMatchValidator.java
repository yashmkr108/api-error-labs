package com.yash.api_error_lab.validation.validator;

import com.yash.api_error_lab.dto.UserRequest;
import com.yash.api_error_lab.validation.annotation.PasswordsMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, UserRequest> {

    @Override
    public boolean isValid(UserRequest request, ConstraintValidatorContext context){

        if(request == null){
            return true;
        }

        if(request.password() == null || request.confirmPassword()==null){
            return true;
        }

        return request.password().equals(request.confirmPassword());
    }
}
