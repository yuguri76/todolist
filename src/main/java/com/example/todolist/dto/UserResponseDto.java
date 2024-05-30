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
    private String accessToken;
    private String refreshToken;

    // 필요한 경우 일부 필드만 포함하는 생성자 추가
    public UserResponseDto(Long id, String username, String nickname, String role, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.role = role;
        this.createdAt = createdAt;
    }
}