package ru.otus.erinary.studentapp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.erinary.studentapp.service.QuizService;


public class AppInitializer {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        QuizService controller = context.getBean(QuizService.class);
        controller.start();
    }

}
