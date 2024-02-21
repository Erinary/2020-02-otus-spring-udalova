package ru.otus.erinary.hw01.quiz;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.erinary.hw01.quiz.service.QuizService;

/**
 * HW-01 - Simple quiz program.
 */
public class AppInitializer {

    private static final String CONTEXT = "/spring-context.xml";

    /**
     * Configures and starts the application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        var context = new ClassPathXmlApplicationContext(CONTEXT);
        var quiz = context.getBean(QuizService.class);
        quiz.start();
    }
}
