package ru.otus.erinary.hw02.quiz;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.erinary.hw02.quiz.service.QuizService;

/**
 * HW-02 - Simple quiz program. Java + Annotation-based config.
 */
public class AppInitializer {

    /**
     * Configures and starts the application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);
        var quizService = context.getBean(QuizService.class);
        quizService.start();
    }

}
