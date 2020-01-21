package ru.otus.erinary.studentapp.service;

import org.springframework.stereotype.Service;
import ru.otus.erinary.studentapp.model.Exercise;
import ru.otus.erinary.studentapp.model.User;
import ru.otus.erinary.studentapp.service.exercise.ExerciseService;
import ru.otus.erinary.studentapp.service.interaction.UserInteractionService;
import ru.otus.erinary.studentapp.service.localization.LocalizationService;

import java.util.List;

/**
 * Сервис для запуска викторины
 */
@Service
public class QuizService {

    private final static String QUIZ_COMMAND = "-quiz";
    private final static String HELP_COMMAND = "-help";
    private final static String QUIT_COMMAND = "-quit";

    private final ExerciseService exerciseService;
    private final LocalizationService localizationService;
    private final UserInteractionService userInteractionService;

    public QuizService(ExerciseService exerciseService, LocalizationService localizationService, UserInteractionService userInteractionService) {
        this.exerciseService = exerciseService;
        this.localizationService = localizationService;
        this.userInteractionService = userInteractionService;
    }

    public void start() {
        System.out.println(localizationService.localizeMessage("message.greeting"));
        help();
        System.out.println(localizationService.localizeMessage("message.input.user"));
        User user = userInteractionService.getUser();

        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.println(localizationService.localizeMessage("message.input.command"));
            String command = userInteractionService.readCommand();
            if (QUIZ_COMMAND.equals(command)) {
                quiz(user);
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

    private void quiz(User user) {
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
            String answer = userInteractionService.readCommand();
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
