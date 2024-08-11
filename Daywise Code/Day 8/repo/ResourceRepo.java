package com.kenny.sdeappbackend.repo;

import com.kenny.sdeappbackend.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepo extends JpaRepository<Resource,Long> {
}
