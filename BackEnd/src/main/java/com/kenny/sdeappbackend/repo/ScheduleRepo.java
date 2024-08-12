package com.kenny.sdeappbackend.repo;

import com.kenny.sdeappbackend.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepo extends JpaRepository<Schedule, Long> {
}
