package com.kenny.sdeappbackend.repo;

import com.kenny.sdeappbackend.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepo extends JpaRepository<Feedback,Long> {
}
