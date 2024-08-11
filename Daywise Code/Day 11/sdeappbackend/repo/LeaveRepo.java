package com.kenny.sdeappbackend.repo;

import com.kenny.sdeappbackend.model.LeaveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRepo extends JpaRepository<LeaveEntity, Long> {
}
