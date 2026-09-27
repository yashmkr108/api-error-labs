package com.yash.api_error_lab.mapper;

import com.yash.api_error_lab.dto.UserResponse;
import com.yash.api_error_lab.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);
}
