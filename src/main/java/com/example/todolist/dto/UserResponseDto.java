package com.example.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private String nickname;
    private String role;
    private LocalDateTime createdAt;
    private String token; // JWT 토큰 필드 추가

    // 토큰 없이 생성할 수 있는 생성자 추가
    public UserResponseDto(Long id, String username, String nickname, String role, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.role = role;
        this.createdAt = createdAt;
    }
}