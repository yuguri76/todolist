package com.example.todolist.service;

import com.example.todolist.dto.ScheduleRequestDto;
import com.example.todolist.dto.ScheduleResponseDto;
import com.example.todolist.exception.ResourceNotFoundException;
import com.example.todolist.exception.UnauthorizedException;
import com.example.todolist.model.Schedule;
import com.example.todolist.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto, String username) {
        Schedule schedule = new Schedule();
        schedule.setTitle(scheduleRequestDto.getTitle());
        schedule.setContent(scheduleRequestDto.getContent());
        schedule.setResponsible(username);
        schedule.setPassword(scheduleRequestDto.getPassword());
        schedule.setCreatedAt(LocalDateTime.now());

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getTitle(), savedSchedule.getContent(), savedSchedule.getResponsible(), savedSchedule.getCreatedAt());
    }

    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("일정을 찾을 수 없습니다."));
        return new ScheduleResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContent(), schedule.getResponsible(), schedule.getCreatedAt());
    }

    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(schedule -> new ScheduleResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContent(), schedule.getResponsible(), schedule.getCreatedAt()))
                .sorted((s1, s2) -> s2.getCreatedAt().compareTo(s1.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto, String username) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("일정을 찾을 수 없습니다."));
        if (!schedule.getResponsible().equals(username)) {
            throw new UnauthorizedException("작성자만 수정할 수 있습니다.");
        }

        schedule.setTitle(scheduleRequestDto.getTitle());
        schedule.setContent(scheduleRequestDto.getContent());

        Schedule updatedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(updatedSchedule.getId(), updatedSchedule.getTitle(), updatedSchedule.getContent(), updatedSchedule.getResponsible(), updatedSchedule.getCreatedAt());
    }

    public void deleteSchedule(Long id, String username) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("일정을 찾을 수 없습니다."));
        if (!schedule.getResponsible().equals(username)) {
            throw new UnauthorizedException("작성자만 삭제할 수 있습니다.");
        }
        scheduleRepository.delete(schedule);
    }
}