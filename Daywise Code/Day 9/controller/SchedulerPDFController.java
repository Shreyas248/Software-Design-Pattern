package com.kenny.sdeappbackend.controller;

import com.kenny.sdeappbackend.model.Scheduler;
import com.kenny.sdeappbackend.service.PDFService;
import com.kenny.sdeappbackend.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class SchedulerPDFController {

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private PDFService pdfService;

    @GetMapping("/generate-pdf")
    public ResponseEntity<InputStreamResource> generateSchedulesPDF() {
        List<Scheduler> schedules = schedulerService.getAllSchedules(); // Assuming there's a method to get all schedules
        ByteArrayInputStream bis = pdfService.generateSchedulePDF(schedules);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=schedules.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
