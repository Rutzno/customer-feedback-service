package com.diarpy.customerfeedbackservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
    private static final Logger LOG = LoggerFactory.getLogger(FeedbackService.class);
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

    public Map<String, Object> findAllFeedbacks(int page, int perPage,
                                                Integer rating, String customer, String product, String vendor) {
        if (page < 1) page = 1;
        if (perPage < 5 || perPage > 20) perPage = 10;
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page-1, perPage, sortById);
        Page<Feedback> p;
        if (rating == null && customer == null && product == null && vendor == null) {
            p = feedbackRepository.findAll(pageable);
        } else {
//            Feedback probe = new Feedback(null, rating, null, customer, product, vendor);
            Feedback probe = new Feedback();
            if (rating != null) probe.setRating(rating);
            if (customer != null) probe.setCustomer(customer);
            if (product != null) probe.setProduct(product);
            if (vendor != null) probe.setVendor(vendor);
            LOG.info("probe : {}", probe);
            ExampleMatcher matcher = ExampleMatcher.matchingAll()
                    .withIgnoreCase("customer", "product")
                    .withMatcher("rating", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withIgnorePaths("id", "feedback");
            Example<Feedback> example = Example.of(probe, matcher);
            p = feedbackRepository.findAll(example, pageable); // retrieve the filtered page of feedbacks
        }

        LOG.info("total_documents : {}", p.getTotalElements());
        LOG.info("page numOfElements : {}", p.getNumberOfElements());
        LOG.info("page content : {}", p.getContent());

        return Map.of(
                "total_documents", p.getTotalElements(),
                "is_first_page", p.isFirst(),
                "is_last_page", p.isLast(),
                "documents", p.getContent()
        );
    }
}