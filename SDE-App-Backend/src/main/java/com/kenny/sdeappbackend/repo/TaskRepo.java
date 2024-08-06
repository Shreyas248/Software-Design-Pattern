package com.kenny.sdeappbackend.repo;

import com.kenny.sdeappbackend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Long> {
}
