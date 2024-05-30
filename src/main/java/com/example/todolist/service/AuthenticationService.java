package com.example.todolist.service;

import com.example.todolist.dto.UserLoginDto;
import com.example.todolist.dto.UserResponseDto;
import com.example.todolist.exception.UserNotFoundException;
import com.example.todolist.exception.UsernameAlreadyExistsException;
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
        String accessToken = jwtUtil.generateToken(user.getUsername(), false);
        String refreshToken = jwtUtil.generateToken(user.getUsername(), true);

        return new UserResponseDto(user.getId(), user.getUsername(), user.getNickname(), user.getRole(), user.getCreatedAt(), accessToken, refreshToken);
    }

    public String refresh(String refreshToken) {
        if (jwtUtil.isTokenExpired(refreshToken)) {
            throw new UserNotFoundException("Refresh Token이 만료되었습니다. 다시 로그인해 주세요.");
        }
        String username = jwtUtil.extractUsername(refreshToken);
        return jwtUtil.generateToken(username, false);
    }
}