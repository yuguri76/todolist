package com.example.todolist.service;

import com.example.todolist.dto.UserRequestDto;
import com.example.todolist.dto.UserResponseDto;
import com.example.todolist.exception.UsernameAlreadyExistsException;
import com.example.todolist.model.User;
import com.example.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-z0-9]{4,10}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9]{8,15}$");

    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        if (!USERNAME_PATTERN.matcher(userRequestDto.getUsername()).matches()) {
            throw new IllegalArgumentException("username은 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 합니다.");
        }

        if (!PASSWORD_PATTERN.matcher(userRequestDto.getPassword()).matches()) {
            throw new IllegalArgumentException("password는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성되어야 합니다.");
        }

        if (userRepository.existsByUsername(userRequestDto.getUsername())) {
            throw new UsernameAlreadyExistsException("중복된 username 입니다.");
        }

        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(userRequestDto.getPassword());
        user.setNickname(userRequestDto.getNickname());
        user.setRole("USER");
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getNickname(), savedUser.getRole(), savedUser.getCreatedAt());
    }
}