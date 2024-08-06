package com.kenny.sdeappbackend.controller;

import com.kenny.sdeappbackend.exception.ResourceNotFoundException;
import com.kenny.sdeappbackend.model.LeaveEntity;
import com.kenny.sdeappbackend.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @GetMapping
    public List<LeaveEntity> getAllLeaves() {
        return leaveService.getAllLeaves();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveEntity> getLeaveById(@PathVariable Long id) {
        return leaveService.getLeaveById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public LeaveEntity createLeave(@RequestBody LeaveEntity leaveEntity) {
        return leaveService.createLeave(leaveEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveEntity> updateLeave(@PathVariable Long id, @RequestBody LeaveEntity leaveDetails) {
        try {
            LeaveEntity updatedLeave = leaveService.updateLeave(id, leaveDetails);
            return ResponseEntity.ok(updatedLeave);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeave(@PathVariable Long id) {
        leaveService.deleteLeave(id);
        return ResponseEntity.noContent().build();
    }
}
