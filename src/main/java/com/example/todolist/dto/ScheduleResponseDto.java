package com.example.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String content;
    private String responsible;
    private LocalDateTime createdAt;
}