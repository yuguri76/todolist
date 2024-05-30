package com.example.todolist.service;

import com.example.todolist.dto.UserRequestDto;
import com.example.todolist.dto.UserResponseDto;
import com.example.todolist.exception.UsernameAlreadyExistsException;
import com.example.todolist.model.User;
import com.example.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        if (userRepository.existsByUsername(userRequestDto.getUsername())) {
            throw new UsernameAlreadyExistsException("중복된 username 입니다.");
        }

        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setNickname(userRequestDto.getNickname());
        user.setRole("USER");
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getNickname(), savedUser.getRole(), savedUser.getCreatedAt());
    }
}