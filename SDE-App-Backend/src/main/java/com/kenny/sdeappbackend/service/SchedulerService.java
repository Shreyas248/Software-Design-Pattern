package com.kenny.sdeappbackend.service;
import com.kenny.sdeappbackend.model.Schedule;
import com.kenny.sdeappbackend.model.Scheduler;
import com.kenny.sdeappbackend.model.Staff;
import com.kenny.sdeappbackend.model.Task;
import com.kenny.sdeappbackend.repo.SchedulerRepo;
import com.kenny.sdeappbackend.repo.StaffRepo;
import com.kenny.sdeappbackend.repo.TaskRepo;
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
    private StaffRepo staffRepo;

    @Autowired
    private TaskRepo taskRepo;

    public List<Scheduler> generateSchedules(Task task) {
        if (task.getNumberOfWorkingDays() <= 0) {
            throw new IllegalArgumentException("Number of working days must be greater than zero.");
        }

        List<Scheduler> schedules = new ArrayList<>();
        List<Staff> availableStaff = getAvailableStaff(task.getStaffNames());

        // Log the staff names being queried
        logger.info("Queried staff names: {}", task.getStaffNames());
        logger.info("Available staff: {}", availableStaff);

        if (availableStaff.isEmpty()) {
            throw new IllegalArgumentException("No staff available for scheduling.");
        }

        List<String> locations = task.getLocations();
        int totalLocations = locations.size();

        if (totalLocations == 0) {
            throw new IllegalArgumentException("No locations provided.");
        }

        int numberOfWorkingDays = task.getNumberOfWorkingDays();
        int locationsPerDay = totalLocations / numberOfWorkingDays;
        if (locationsPerDay == 0 && totalLocations > 0) {
            locationsPerDay = 1; // Ensure at least one location per day if the number of days is greater than total locations
        }

        LocalDateTime startDate = LocalDateTime.now();

        for (int day = 0; day < numberOfWorkingDays; day++) {
            for (int i = 0; i < locationsPerDay && (day * locationsPerDay + i) < totalLocations; i++) {
                String currentLocation = locations.get(day * locationsPerDay + i);
                Staff assignedStaff = availableStaff.get(i % availableStaff.size());

                Scheduler schedule = Scheduler.builder()
                        .title(task.getName() + " - " + currentLocation)
                        .startTime(startDate.plusDays(day).withHour(9).withMinute(0))  // Example: 9 AM start
                        .endTime(startDate.plusDays(day).withHour(10).withMinute(0))   // Example: 10 AM end
                        .staff(assignedStaff)
                        .build();

                schedules.add(schedule);
                scheduleRepo.save(schedule);
            }
        }
        return schedules;
    }

    private List<Staff> getAvailableStaff(List<String> staffNames) {
        // Log staff names being queried
        List<Staff> staffList = staffRepo.findByNameIn(staffNames);
        logger.info("Staff retrieved from repository: {}", staffList);
        return staffList;
    }
}
