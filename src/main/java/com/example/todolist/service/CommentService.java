package com.example.todolist.service;

import com.example.todolist.dto.CommentRequestDto;
import com.example.todolist.dto.CommentResponseDto;
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
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(commentRequestDto.getScheduleId());
        if (!scheduleOptional.isPresent()) {
            throw new RuntimeException("Schedule not found");
        }

        Schedule schedule = scheduleOptional.get();

        Comment comment = new Comment();
        comment.setContent(commentRequestDto.getContent());
        comment.setUserId(commentRequestDto.getUserId());
        comment.setSchedule(schedule);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment.getId(), savedComment.getContent(), savedComment.getUserId(), savedComment.getSchedule().getId(), savedComment.getCreatedAt());
    }

    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setContent(commentRequestDto.getContent());

        Comment updatedComment = commentRepository.save(comment);

        return new CommentResponseDto(updatedComment.getId(), updatedComment.getContent(), updatedComment.getUserId(), updatedComment.getSchedule().getId(), updatedComment.getCreatedAt());
    }

    public void deleteComment(Long id, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));

        // 사용자가 댓글 작성자와 동일한지 확인
        if (!comment.getUserId().equals(commentRequestDto.getUserId())) {
            throw new RuntimeException("User mismatch");
        }

        commentRepository.delete(comment);
    }
}