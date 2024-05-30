package com.example.todolist.controller;

import com.example.todolist.dto.CommentRequestDto;
import com.example.todolist.dto.CommentResponseDto;
import com.example.todolist.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public CommentResponseDto createComment(@Valid @RequestBody CommentRequestDto commentRequestDto, Authentication authentication) {
        String username = authentication.getName();
        commentRequestDto.setUserId(username);
        return commentService.createComment(commentRequestDto);
    }

    @PutMapping("/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @Valid @RequestBody CommentRequestDto commentRequestDto, Authentication authentication) {
        String username = authentication.getName();
        commentRequestDto.setUserId(username);
        return commentService.updateComment(id, commentRequestDto, username);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        commentService.deleteComment(id, username);
    }
}