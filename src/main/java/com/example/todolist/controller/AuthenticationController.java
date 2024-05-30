package com.example.todolist.controller;

import com.example.todolist.dto.UserLoginDto;
import com.example.todolist.dto.UserResponseDto;
import com.example.todolist.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
        UserResponseDto userResponseDto = authenticationService.login(userLoginDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + userResponseDto.getAccessToken());
        return ResponseEntity.ok().headers(headers).body(userResponseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        String newAccessToken = authenticationService.refresh(refreshToken);
        Map<String, String> response = new HashMap<>();
        response.put("accessToken", newAccessToken);
        response.put("refreshToken", refreshToken); // Optionally, send the same refresh token back
        return ResponseEntity.ok(response);
    }
}