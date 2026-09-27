package com.yash.api_error_lab.service;

import com.yash.api_error_lab.dto.UserRequest;
import com.yash.api_error_lab.dto.UserResponse;
import com.yash.api_error_lab.entity.User;
import com.yash.api_error_lab.exception.EmailAlreadyExistsException;
import com.yash.api_error_lab.exception.UserNotFoundException;
import com.yash.api_error_lab.mapper.UserMapper;
import com.yash.api_error_lab.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserResponse createUser(UserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        User user = new User(request.name(), request.email(), request.age(), request.phone(), request.password());

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    public UserResponse findById(Long id) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return userMapper.toResponse(existingUser);
    }

    public List<UserResponse> getAllUser() {

        List<User> users = userRepository.findAll();

        return users.stream().map(userMapper::toResponse).toList();
    }

    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        existingUser.updateProfile(request.name(), request.email(), request.phone(), request.age(), request.password());

        return userMapper.toResponse(existingUser);
    }

    public void deleteUser(Long id) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(existingUser);
    }
}
