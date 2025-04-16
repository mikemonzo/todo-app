package com.example.todo.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.todo.user.dto.CreateUserRequest;
import com.example.todo.user.model.User;
import com.example.todo.user.model.UserRepository;
import com.example.todo.user.model.UserRole;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(CreateUserRequest request) {
        return userRepository.save(User.builder().username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword())).email(request.getEmail())
                .fullName(request.getFullname()).role(UserRole.USER).build());
    }

    public User changeRole(User user, UserRole role) {
        user.setRole(role);
        return userRepository.save(user);
    }

    public User changeRole(Long id, UserRole role) {
        return userRepository.findById(id).map(u -> {
            u.setRole(role);
            return userRepository.save(u);
        }).orElse(null);
    }
}
