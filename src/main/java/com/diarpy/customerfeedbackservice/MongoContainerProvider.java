package com.diarpy.customerfeedbackservice;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.MongoDBContainer;

import java.util.List;

/**
 * @author Mack_TB
 * @since 10/05/2024
 * @version 1.0.1
 */

/**
 * Make a MongoDB container run upon the application startup and stop
 * upon the application shutdown
 */

@Component
public class MongoContainerProvider {
    private final MongoDBContainer container;

    public MongoContainerProvider(@Value("${mongodb.image}") String mongodbImage) {
        this.container = new MongoDBContainer(mongodbImage); // image name
        container.withCreateContainerCmdModifier(cmd -> cmd.withName("feedback-service")); // container name
        container.addEnv("MONGO_INITDB_DATABASE", "feedback_db"); // init database
        container.setPortBindings(List.of("27017:27017")); // expose port 27017
        container.start();
    }

    @PreDestroy
    public void tearDown() {
        container.stop();
    }
}
