package com.example.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @NotNull(message = "일정 id를 입력해주세요.")
    private Long scheduleId;

    private String userId;
}