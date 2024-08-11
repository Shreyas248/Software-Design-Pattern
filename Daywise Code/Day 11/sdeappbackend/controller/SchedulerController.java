package com.kenny.sdeappbackend.controller;

import com.kenny.sdeappbackend.dto.ScheduleRequestDTO;
import com.kenny.sdeappbackend.model.Scheduler;
import com.kenny.sdeappbackend.service.PDFService;
import com.kenny.sdeappbackend.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class SchedulerController {

    @Autowired
    private SchedulerService scheduleService;

    @Autowired
    private PDFService pdfService;

    @PostMapping("/generate")
    public List<Scheduler> generateSchedules(@RequestBody ScheduleRequestDTO scheduleRequest) {
        return scheduleService.generateSchedules(scheduleRequest);
    }

    @GetMapping("/download-pdf")
    public ResponseEntity<Resource> downloadSchedulePDF() {
        List<Scheduler> schedules = scheduleService.getAllSchedules();
        ByteArrayInputStream pdfStream = pdfService.generateSchedulePDF(schedules);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=schedule.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
}
