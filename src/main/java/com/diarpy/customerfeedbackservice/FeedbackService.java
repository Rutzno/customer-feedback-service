package com.diarpy.customerfeedbackservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mack_TB
 * @since 10/05/2024
 * @version 1.0.2
 */

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public ResponseEntity<Feedback> findFeedbackById(String id) {
        Feedback feedback = feedbackRepository.findById(id).orElse(null);
        return feedback == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(feedback);
    }

    public List<Feedback> findAllFeedbacks() {
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        return feedbackRepository.findAll(sortById);
    }
}
