package ru.otus.erinary.studentapp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.erinary.studentapp.controller.ExerciseController;


public class AppInitializer {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ExerciseController controller = context.getBean(ExerciseController.class);
    }

}
