package ru.otus.erinary.hw01.quiz;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.erinary.hw01.quiz.service.QuizService;

/**
 * ДЗ-01 - Программа для тестирования (викторины).
 */
public class AppInitializer {

    private static final String CONTEXT = "/spring-context.xml";

    /**
     * Выполняет конфигурацию и запуск приложения.
     *
     * @param args Аргументы командной строки
     */
    public static void main(final String[] args) {
        var context = new ClassPathXmlApplicationContext(CONTEXT);
        var quiz = context.getBean(QuizService.class);
        quiz.start();
    }
}
