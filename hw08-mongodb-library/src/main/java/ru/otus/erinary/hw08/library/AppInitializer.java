package ru.otus.erinary.hw08.library;

import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * HW08 - Small app represents a library storage system with four main entities: author, book, genre and
 * comment. The interface is built on Spring Shell, while Spring Data is used to handle database operations.
 * This time, MongoDB is used instead of a relational database.
 */
@EnableMongock
@EnableMongoRepositories
@SpringBootApplication
public class AppInitializer {

    /**
     * Configures and starts the application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(AppInitializer.class, args);
    }

}
