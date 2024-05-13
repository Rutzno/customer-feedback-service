package com.diarpy.customerfeedbackservice;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOG = LoggerFactory.getLogger(FeedbackController.class);
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
    public Map<String, Object> getFeedbacks(HttpServletRequest request,
                                            @RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "10") int perPage,
                                            @RequestParam(required = false) Integer rating,
                                            @RequestParam(required = false) String customer,
                                            @RequestParam(required = false) String product,
                                            @RequestParam(required = false) String vendor) {
        /*if (rating == null && customer == null && product == null && vendor == null) {
            return feedbackService.findAllFeedbacks(page, perPage);
        }*/
        LOG.info("Path : {}", request.getRequestURI() +"?"+request.getQueryString());
        return feedbackService.findAllFeedbacks(page, perPage, rating, customer, product, vendor);
    }
}