package com.diarpy.customerfeedbackservice;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Mack_TB
 * @since 10/05/2024
 * @version 1.0.1
 */

public interface FeedbackRepository extends MongoRepository<Feedback, String> {

}
