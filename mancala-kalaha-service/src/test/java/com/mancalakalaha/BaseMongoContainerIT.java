package com.mancalakalaha;

import org.junit.ClassRule;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.images.PullPolicy;

public class BaseMongoContainerIT {

    @ClassRule
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5")
            .withEnv("MONGO_INITDB_DATABASE","local")
            .withEnv("MONGO_INIT_ROOT_USERNAME","admin")
            .withEnv("MONGO_INIT_ROOT_PASSWORD","admin")
            .withImagePullPolicy(PullPolicy.alwaysPull());

    static {
        mongoDBContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.port", () -> mongoDBContainer.getFirstMappedPort());
        registry.add("spring.data.mongodb.host", () -> mongoDBContainer.getHost());
    }
}