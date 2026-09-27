package com.yash.api_error_lab.controller;

import com.yash.api_error_lab.dto.UserRequest;
import com.yash.api_error_lab.dto.UserResponse;
import com.yash.api_error_lab.service.UserService;
import com.yash.api_error_lab.validation.group.CreateGroup;
import com.yash.api_error_lab.validation.group.UpdateGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping
    public UserResponse createUser(@Validated(CreateGroup.class) @RequestBody UserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody @Validated(UpdateGroup.class) UserRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
