package com.kenny.sdeappbackend.service;

import com.kenny.sdeappbackend.exception.ResourceNotFoundException;
import com.kenny.sdeappbackend.model.LeaveEntity;
import com.kenny.sdeappbackend.repo.LeaveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepo leaveRepository;

    public List<LeaveEntity> getAllLeaves() {
        return leaveRepository.findAll();
    }

    public Optional<LeaveEntity> getLeaveById(Long id) {
        return leaveRepository.findById(id);
    }

    public LeaveEntity createLeave(LeaveEntity leaveEntity) {
        return leaveRepository.save(leaveEntity);
    }

    public LeaveEntity updateLeave(Long id, LeaveEntity leaveDetails) {
        return leaveRepository.findById(id).map(leaveEntity -> {
            leaveEntity.setStartDate(leaveDetails.getStartDate());
            leaveEntity.setEndDate(leaveDetails.getEndDate());
            leaveEntity.setReason(leaveDetails.getReason());
            leaveEntity.setApproved(leaveDetails.isApproved());
            leaveEntity.setUser(leaveDetails.getUser());
            return leaveRepository.save(leaveEntity);
        }).orElseThrow(() -> new ResourceNotFoundException("Leave not found with id " + id));
    }

    public void deleteLeave(Long id) {
        leaveRepository.deleteById(id);
    }
}
