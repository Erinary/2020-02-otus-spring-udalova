package ru.otus.erinary.hw03.quiz.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.otus.erinary.hw03.quiz.model.Exercise;
import ru.otus.erinary.hw03.quiz.model.User;
import ru.otus.erinary.hw03.quiz.service.exercise.ExerciseService;
import ru.otus.erinary.hw03.quiz.service.interaction.input.InputInteractionService;
import ru.otus.erinary.hw03.quiz.service.interaction.output.OutputInteractionService;
import ru.otus.erinary.hw03.quiz.service.localization.LocalizationService;

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
    private final InputInteractionService inputService;
    private final OutputInteractionService outputService;

    public QuizService(final ExerciseService exerciseService, final LocalizationService localizationService,
                       final InputInteractionService inputService, final OutputInteractionService outputService) {
        this.exerciseService = exerciseService;
        this.localizationService = localizationService;
        this.inputService = inputService;
        this.outputService = outputService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        outputService.sendMessage(localizationService.localizeMessage("message.greeting"));
        help();
        outputService.sendMessage(localizationService.localizeMessage("message.input.user"));
        User user = inputService.getUser();

        //noinspection InfiniteLoopStatement
        while (true) {
            outputService.sendMessage(localizationService.localizeMessage("message.input.command"));
            String command = inputService.readCommand();
            if (QUIZ_COMMAND.equals(command)) {
                quiz(user);
            } else if (QUIT_COMMAND.equals(command)) {
                quit();
            } else if (HELP_COMMAND.equals(command)) {
                help();
            } else {
                outputService.sendMessage(localizationService.localizeMessage("message.unknown.command") + command);
            }
        }
    }

    private void help() {
        outputService.sendMessage(localizationService.localizeMessage("message.help"));
    }

    private void quiz(User user) {
        if (user.getCorrectAnswersCounter() != 0) {
            user.setCorrectAnswersCounter(0);
        }
        outputService.sendMessage(localizationService.localizeMessage("message.quiz.start"));
        List<Exercise> exercises = exerciseService.getExercises();
        exercises.forEach(exercise -> {
            outputService.sendMessage(localizationService.localizeMessage("message.question"));
            outputService.sendMessage(exercise.getQuestion());
            exercise.getResponses().forEach(System.out::println);

            outputService.sendMessage(localizationService.localizeMessage("message.answer"));
            String answer = inputService.readCommand();
            if (exerciseService.checkAnswer(exercise, answer)) {
                outputService.sendMessage(localizationService.localizeMessage("message.answer.correct"));
                user.raiseAnswersCounter();
            } else {
                outputService.sendMessage(localizationService.localizeMessage("message.answer.wrong"));
            }
        });
        outputService.sendMessage(localizationService.localizeMessage("message.quiz.end")
                + user.getCorrectAnswersCounter());
    }

    private void quit() {
        outputService.sendMessage(localizationService.localizeMessage("messages.quit"));
        System.exit(-1);
    }
}
