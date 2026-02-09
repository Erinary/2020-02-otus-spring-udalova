package ru.otus.erinary.hw08.library.dao.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class MongoTestcontainersConfig {

    @Container
    private static final MongoDBContainer MONGO = new MongoDBContainer("mongo:7.0")
            .withReuse(true);

    @DynamicPropertySource
    static void mongoProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", () -> MONGO.getReplicaSetUrl("test-library"));
    }
}
