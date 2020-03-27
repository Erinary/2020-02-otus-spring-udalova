package ru.otus.erinary.studentapp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.erinary.studentapp.service.QuizService;

public class AppInitializer {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        QuizService quizService = context.getBean(QuizService.class);
        quizService.start();
    }

}
