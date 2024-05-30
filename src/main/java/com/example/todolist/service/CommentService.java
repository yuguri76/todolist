package com.example.todolist.service;

import com.example.todolist.dto.CommentRequestDto;
import com.example.todolist.dto.CommentResponseDto;
import com.example.todolist.exception.ResourceNotFoundException;
import com.example.todolist.exception.UnauthorizedException;
import com.example.todolist.model.Comment;
import com.example.todolist.model.Schedule;
import com.example.todolist.repository.CommentRepository;
import com.example.todolist.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        Schedule schedule = scheduleRepository.findById(commentRequestDto.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("일정을 찾을 수 없습니다."));

        Comment comment = new Comment();
        comment.setContent(commentRequestDto.getContent());
        comment.setUserId(commentRequestDto.getUserId());
        comment.setSchedule(schedule);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment.getId(), savedComment.getContent(), savedComment.getUserId(), savedComment.getSchedule().getId(), savedComment.getCreatedAt());
    }

    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, String username) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("댓글을 찾을 수 없습니다."));

        if (!comment.getUserId().equals(username)) {
            throw new UnauthorizedException("작성자만 수정할 수 있습니다.");
        }

        comment.setContent(commentRequestDto.getContent());
        Comment updatedComment = commentRepository.save(comment);

        return new CommentResponseDto(updatedComment.getId(), updatedComment.getContent(), updatedComment.getUserId(), updatedComment.getSchedule().getId(), updatedComment.getCreatedAt());
    }

    public void deleteComment(Long id, String username) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("댓글을 찾을 수 없습니다."));

        if (!comment.getUserId().equals(username)) {
            throw new UnauthorizedException("작성자만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }
}