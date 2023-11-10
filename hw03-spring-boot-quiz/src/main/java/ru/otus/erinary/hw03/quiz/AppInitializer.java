package ru.otus.erinary.hw03.quiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.erinary.hw03.quiz.service.QuizService;

/**
 * HW-03 - Simple quiz program based on Spring Boot.
 */
@SpringBootApplication
public class AppInitializer {

    /**
     * Configures and starts the application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        var context = SpringApplication.run(AppInitializer.class, args);
        var quizService = context.getBean(QuizService.class);
        quizService.start();
    }

}
