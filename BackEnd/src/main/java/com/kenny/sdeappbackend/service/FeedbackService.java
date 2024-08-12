package com.kenny.sdeappbackend.service;

import com.kenny.sdeappbackend.exception.ResourceNotFoundException;
import com.kenny.sdeappbackend.model.Feedback;
import com.kenny.sdeappbackend.repo.FeedbackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepo feedbackRepository;

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public Optional<Feedback> getFeedbackById(Long id) {
        return feedbackRepository.findById(id);
    }

    public Feedback createFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public Feedback updateFeedback(Long id, Feedback feedbackDetails) {
        return feedbackRepository.findById(id).map(feedback -> {
            feedback.setContent(feedbackDetails.getContent());
            feedback.setRating(feedbackDetails.getRating());
            feedback.setUser(feedbackDetails.getUser());
            return feedbackRepository.save(feedback);
        }).orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id " + id));
    }

    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }
}
