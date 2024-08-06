package com.kenny.sdeappbackend.repo;

import com.kenny.sdeappbackend.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepo extends JpaRepository<Staff, Long> {
    List<Staff> findByNameIn(List<String> names);
}
