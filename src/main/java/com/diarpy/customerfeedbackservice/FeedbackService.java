package com.diarpy.customerfeedbackservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Mack_TB
 * @since 10/05/2024
 * @version 1.0.4
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

    public Map<String, Object> findAllFeedbacks(int page, int perPage) {
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        if (page < 1) page = 1;
        if (perPage < 5 || perPage > 20) perPage = 10;
        Pageable pageable = PageRequest.of(page-1, perPage, sortById);
        Page<Feedback> p = feedbackRepository.findAll(pageable);
        Map<String, Object> response = Map.of(
                "total_documents", p.getTotalElements(),
                "is_first_page", p.isFirst(),
                "is_last_page", p.isLast(),
                "documents", p.getContent()
        );
        return response;
    }
}