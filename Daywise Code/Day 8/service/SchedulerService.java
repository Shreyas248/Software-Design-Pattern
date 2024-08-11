package com.kenny.sdeappbackend.service;

import com.kenny.sdeappbackend.dto.ScheduleRequestDTO;
import com.kenny.sdeappbackend.enums.Role;
import com.kenny.sdeappbackend.model.Scheduler;
import com.kenny.sdeappbackend.model.User;
import com.kenny.sdeappbackend.repo.SchedulerRepo;
import com.kenny.sdeappbackend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    @Autowired
    private SchedulerRepo scheduleRepo;

    @Autowired
    private UserRepo userRepo;

    public List<Scheduler> generateSchedules(ScheduleRequestDTO scheduleRequest) {
        if (scheduleRequest.getNumberOfWorkingDays() <= 0) {
            throw new IllegalArgumentException("Number of working days must be greater than zero.");
        }

        List<Scheduler> schedules = new ArrayList<>();
        List<User> availableUsers = getAvailableUsers(scheduleRequest.getStaffNames());

        // Log the staff names being queried
        logger.info("Queried staff names: {}", scheduleRequest.getStaffNames());
        logger.info("Available staff: {}", availableUsers);

        if (availableUsers.isEmpty()) {
            throw new IllegalArgumentException("No staff available for scheduling.");
        }

        List<String> locations = scheduleRequest.getLocations();
        int totalLocations = locations.size();

        if (totalLocations == 0) {
            throw new IllegalArgumentException("No locations provided.");
        }

        int numberOfWorkingDays = scheduleRequest.getNumberOfWorkingDays();
        int locationsPerDay = totalLocations / numberOfWorkingDays;
        if (locationsPerDay == 0 && totalLocations > 0) {
            locationsPerDay = 1; // Ensure at least one location per day if the number of days is greater than total locations
        }

        LocalDateTime startDate = LocalDateTime.now();

        for (int day = 0; day < numberOfWorkingDays; day++) {
            for (int i = 0; i < locationsPerDay && (day * locationsPerDay + i) < totalLocations; i++) {
                String currentLocation = locations.get(day * locationsPerDay + i);
                User assignedUser = availableUsers.get(i % availableUsers.size());

                Scheduler schedule = Scheduler.builder()
                        .title(scheduleRequest.getTaskName() + " - " + currentLocation)
                        .startTime(startDate.plusDays(day).withHour(9).withMinute(0))  // Example: 9 AM start
                        .endTime(startDate.plusDays(day).withHour(10).withMinute(0))   // Example: 10 AM end
                        .user(assignedUser)
                        .build();

                schedules.add(schedule);
                scheduleRepo.save(schedule);
            }
        }
        return schedules;
    }

    private List<User> getAvailableUsers(List<String> staffNames) {
        Role role = Role.USER;
        List<User> userList = userRepo.findByNameInAndRole(staffNames, role);
        logger.info("Users retrieved from repository: {}", userList);
        return userList;
    }

    public List<Scheduler> getAllSchedules() {
        return scheduleRepo.findAll();
    }
}
