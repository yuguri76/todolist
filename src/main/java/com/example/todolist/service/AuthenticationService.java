package com.example.todolist.service;

import com.example.todolist.dto.UserLoginDto;
import com.example.todolist.dto.UserResponseDto;
import com.example.todolist.exception.UserNotFoundException;
import com.example.todolist.model.User;
import com.example.todolist.repository.UserRepository;
import com.example.todolist.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public UserResponseDto login(UserLoginDto userLoginDto) {
        Optional<User> userOptional = userRepository.findByUsername(userLoginDto.getUsername());

        if (!userOptional.isPresent() || !userOptional.get().getPassword().equals(userLoginDto.getPassword())) {
            throw new UserNotFoundException("회원을 찾을 수 없습니다.");
        }

        User user = userOptional.get();
        String token = jwtUtil.generateToken(user.getUsername());

        return new UserResponseDto(user.getId(), user.getUsername(), user.getNickname(), user.getRole(), user.getCreatedAt(), token);
    }
}