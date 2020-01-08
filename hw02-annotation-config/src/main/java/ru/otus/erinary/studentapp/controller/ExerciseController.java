package ru.otus.erinary.studentapp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import ru.otus.erinary.studentapp.model.Exercise;
import ru.otus.erinary.studentapp.model.User;
import ru.otus.erinary.studentapp.service.ExerciseService;
import ru.otus.erinary.studentapp.service.LocalizationService;

import java.util.List;
import java.util.Scanner;

/**
 * Контроллер для взаимодействия с пользователем через консоль
 */
@Controller
public class ExerciseController {

    private final static String QUIZ_COMMAND = "-quiz";
    private final static String HELP_COMMAND = "-help";
    private final static String QUIT_COMMAND = "-quit";

    private final ExerciseService exerciseService;
    private final LocalizationService localizationService;

    public ExerciseController(ExerciseService exerciseService, LocalizationService localizationService) {
        this.exerciseService = exerciseService;
        this.localizationService = localizationService;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(localizationService.localizeMessage("message.greeting"));
        help();
        System.out.println(localizationService.localizeMessage("message.input.name"));
        String name = scanner.nextLine();
        System.out.println(localizationService.localizeMessage("message.input.surname"));
        String surname = scanner.nextLine();
        User user = new User(name, surname);

        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.println(localizationService.localizeMessage("message.input.command"));
            String command = scanner.nextLine();
            if (QUIZ_COMMAND.equals(command)) {
                quiz(scanner, user);
            } else if (QUIT_COMMAND.equals(command)) {
                quit();
            } else if (HELP_COMMAND.equals(command)) {
                help();
            } else {
                System.out.println(localizationService.localizeMessage("message.unknown.command") + command);
            }
        }
    }

    private void help() {
        System.out.println(localizationService.localizeMessage("message.help"));
    }

    private void quiz(Scanner scanner, User user) {
        if (user.getCorrectAnswersCounter() != 0) {
            user.setCorrectAnswersCounter(0);
        }
        System.out.println(localizationService.localizeMessage("message.quiz.start"));
        List<Exercise> exercises = exerciseService.getExercises();
        exercises.forEach(exercise -> {
            System.out.println(localizationService.localizeMessage("message.question"));
            System.out.println(exercise.getQuestion());
            exercise.getResponses().forEach(System.out::println);

            System.out.println(localizationService.localizeMessage("message.answer"));
            String answer = scanner.nextLine();
            if (exerciseService.checkAnswer(exercise, answer)) {
                System.out.println(localizationService.localizeMessage("message.answer.correct"));
                user.raiseAnswersCounter();
            } else {
                System.out.println(localizationService.localizeMessage("message.answer.wrong"));
            }
        });
        System.out.println(localizationService.localizeMessage("message.quiz.end")
                + user.getCorrectAnswersCounter());
    }

    private void quit() {
        System.out.println(localizationService.localizeMessage("messages.quit"));
        System.exit(-1);
    }
}
