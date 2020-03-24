package ru.otus.erinary.hw01.studentapp;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.erinary.hw01.studentapp.service.QuizService;

public class AppInitializer {

    private static final String CONTEXT = "/spring-context.xml";

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(CONTEXT);
        QuizService quiz = context.getBean(QuizService.class);
        quiz.start();
    }
}
