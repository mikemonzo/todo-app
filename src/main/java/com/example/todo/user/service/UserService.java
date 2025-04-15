package com.example.todo.user.service;

import org.springframework.stereotype.Service;
import com.example.todo.user.model.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
}
