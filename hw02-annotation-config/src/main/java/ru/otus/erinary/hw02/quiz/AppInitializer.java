package ru.otus.erinary.hw02.quiz;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.erinary.hw02.quiz.service.QuizService;

/**
 * ДЗ-02 - Программа для тестирования (викторины). Java + Annotation-based конфигурация.
 */
public class AppInitializer {

    /**
     * Выполняет конфигурацию и запуск приложения.
     *
     * @param args Аргументы командной строки
     */
    public static void main(final String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);
        var quizService = context.getBean(QuizService.class);
        quizService.start();
    }

}
