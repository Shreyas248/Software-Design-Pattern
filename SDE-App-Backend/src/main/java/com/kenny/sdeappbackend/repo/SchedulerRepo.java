package com.kenny.sdeappbackend.repo;

import com.kenny.sdeappbackend.model.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulerRepo extends JpaRepository<Scheduler, Long> {
}
