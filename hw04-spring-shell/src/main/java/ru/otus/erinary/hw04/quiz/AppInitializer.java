package ru.otus.erinary.hw04.quiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.erinary.hw04.quiz.service.QuizService;

@SpringBootApplication
public class AppInitializer {

    public static void main(String[] args) {
        var context = SpringApplication.run(AppInitializer.class, args);
        var quizService = context.getBean(QuizService.class);
        quizService.start();
    }

}
