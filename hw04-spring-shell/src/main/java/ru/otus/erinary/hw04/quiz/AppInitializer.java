package ru.otus.erinary.hw04.quiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * HW-04 - Simple quiz program based on Spring Boot with Spring Shell interface.
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
