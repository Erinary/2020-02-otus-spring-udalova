package ru.otus.erinary.hw02.quiz;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.erinary.hw02.quiz.service.QuizService;

public class AppInitializer {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        QuizService quizService = context.getBean(QuizService.class);
        quizService.start();
    }

}
