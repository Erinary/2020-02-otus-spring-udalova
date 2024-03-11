package ru.otus.erinary.hw05.jdbclibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * HW05 - Small app represents a library storage system with three main entities: author, book and genre.
 * The interface is built on Spring Shell, while Spring JDBC is used for interaction with database.
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
