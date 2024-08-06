package com.kenny.sdeappbackend.controller;


import com.kenny.sdeappbackend.model.Schedule;
import com.kenny.sdeappbackend.model.Scheduler;
import com.kenny.sdeappbackend.model.Task;
import com.kenny.sdeappbackend.service.ScheduleService;
import com.kenny.sdeappbackend.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class SchedulerController {

    @Autowired
    private SchedulerService scheduleService;

    @PostMapping("/generate")
    public List<Scheduler> generateSchedules(@RequestBody Task task) {
        return scheduleService.generateSchedules(task);
    }
}

