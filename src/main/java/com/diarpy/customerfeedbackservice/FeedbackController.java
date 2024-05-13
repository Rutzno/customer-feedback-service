package com.diarpy.customerfeedbackservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

/**
 * @author Mack_TB
 * @since 10/05/2024
 * @version 1.0.4
 */

@RestController
public class FeedbackController {
    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/feedback")
    public ResponseEntity<?> postFeedback(@RequestBody Feedback feedback) {
        HttpHeaders headers = new HttpHeaders();
        feedback = feedbackService.saveFeedback(feedback);
        headers.setLocation(URI.create("/feedback/" + feedback.getId()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/feedback/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable String id) {
        return feedbackService.findFeedbackById(id);
    }

    @GetMapping("/feedback")
    public Map<String, Object> getFeedbacks(@RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "10") int perPage) {
        return feedbackService.findAllFeedbacks(page, perPage);
    }
}
