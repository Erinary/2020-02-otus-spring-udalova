package ru.otus.erinary.hw06.hibernatelibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * HW06 - Small app represents a library storage system with four main entities: author, book, genre and
 * comment. The interface is built on Spring Shell, while Hibernate is used as JPA provider for
 * interaction with database.
 */
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
