package com.kenny.sdeappbackend.service;

import com.kenny.sdeappbackend.enums.Role;
import com.kenny.sdeappbackend.exception.ResourceNotFoundException;
import com.kenny.sdeappbackend.model.Schedule;
import com.kenny.sdeappbackend.model.User;
import com.kenny.sdeappbackend.repo.ScheduleRepo;
import com.kenny.sdeappbackend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepo scheduleRepository;

    @Autowired
    private UserRepo userRepository;

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Optional<Schedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    public Schedule createSchedule(Schedule schedule, User currentUser) {
        if (currentUser.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admins can create schedules.");
        }
        schedule.setUser(currentUser);
        return scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(Long id, Schedule scheduleDetails, User currentUser) {
        if (currentUser.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admins can update schedules.");
        }
        return scheduleRepository.findById(id).map(schedule -> {
            schedule.setTitle(scheduleDetails.getTitle());
            schedule.setDate(scheduleDetails.getDate());
            schedule.setTime(scheduleDetails.getTime());
            schedule.setRoom(scheduleDetails.getRoom());
            schedule.setUser(currentUser);
            return scheduleRepository.save(schedule);
        }).orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id " + id));
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}