package ru.otus.erinary.studentapp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.erinary.studentapp.controller.ExerciseController;

@ComponentScan
public class AppInitializer {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppInitializer.class);
        ExerciseController controller = context.getBean(ExerciseController.class);
    }

}
