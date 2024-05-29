package com.example.todolist.controller;

import com.example.todolist.dto.UserLoginDto;
import com.example.todolist.dto.UserResponseDto;
import com.example.todolist.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
        UserResponseDto userResponseDto = authenticationService.login(userLoginDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + userResponseDto.getToken());
        return ResponseEntity.ok().headers(headers).body(userResponseDto);
    }
}