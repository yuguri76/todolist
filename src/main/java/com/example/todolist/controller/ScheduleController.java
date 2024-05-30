package com.example.todolist.controller;

import com.example.todolist.dto.ScheduleRequestDto;
import com.example.todolist.dto.ScheduleResponseDto;
import com.example.todolist.exception.InvalidTokenException;
import com.example.todolist.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleResponseDto createSchedule(@Valid @RequestBody ScheduleRequestDto scheduleRequestDto, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InvalidTokenException("토큰이 유효하지 않습니다.");
        }
        String username = authentication.getName();
        return scheduleService.createSchedule(scheduleRequestDto, username);
    }

    @GetMapping("/{id}")
    public ScheduleResponseDto getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id);
    }

    @GetMapping
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @PutMapping("/{id}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id, @Valid @RequestBody ScheduleRequestDto scheduleRequestDto, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InvalidTokenException("토큰이 유효하지 않습니다.");
        }
        String username = authentication.getName();
        return scheduleService.updateSchedule(id, scheduleRequestDto, username);
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InvalidTokenException("토큰이 유효하지 않습니다.");
        }
        String username = authentication.getName();
        scheduleService.deleteSchedule(id, username);
    }
}