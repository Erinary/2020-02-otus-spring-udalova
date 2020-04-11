package ru.otus.erinary.hw03.quiz.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.otus.erinary.hw03.quiz.model.Exercise;
import ru.otus.erinary.hw03.quiz.model.User;
import ru.otus.erinary.hw03.quiz.service.exercise.ExerciseService;
import ru.otus.erinary.hw03.quiz.service.interaction.InteractionService;
import ru.otus.erinary.hw03.quiz.service.localization.LocalizationService;

import java.util.List;

/**
 * Реализация сервиса {@link QuizService}
 */
@Service
public class QuizServiceImpl implements QuizService {

    private final static String QUIZ_COMMAND = "-quiz";
    private final static String HELP_COMMAND = "-help";
    private final static String QUIT_COMMAND = "-quit";

    private final ExerciseService exerciseService;
    private final LocalizationService localizationService;
    private final InteractionService interactionService;

    public QuizServiceImpl(final ExerciseService exerciseService, final LocalizationService localizationService,
                           final InteractionService interactionService) {
        this.exerciseService = exerciseService;
        this.localizationService = localizationService;
        this.interactionService = interactionService;
    }

    @Override
    public void start() {
        interactionService.sendMessage(localizationService.localizeMessage("message.greeting"));
        help();
        interactionService.sendMessage(localizationService.localizeMessage("message.input.user"));
        User user = createUser();

        //noinspection InfiniteLoopStatement
        while (true) {
            interactionService.sendMessage(localizationService.localizeMessage("message.input.command"));
            String command = interactionService.readLine();
            if (QUIZ_COMMAND.equals(command)) {
                quiz(user);
            } else if (QUIT_COMMAND.equals(command)) {
                quit();
            } else if (HELP_COMMAND.equals(command)) {
                help();
            } else {
                interactionService.sendMessage(localizationService.localizeMessage("message.unknown.command") + command);
            }
        }
    }

    private void help() {
        interactionService.sendMessage(localizationService.localizeMessage("message.help"));
    }

    private void quiz(User user) {
        if (user.getCorrectAnswersCounter() != 0) {
            user.setCorrectAnswersCounter(0);
        }
        interactionService.sendMessage(localizationService.localizeMessage("message.quiz.start"));
        List<Exercise> exercises = exerciseService.getExercises();
        exercises.forEach(exercise -> {
            interactionService.sendMessage(localizationService.localizeMessage("message.question"));
            interactionService.sendMessage(exercise.getQuestion());
            exercise.getResponses().forEach(System.out::println);

            interactionService.sendMessage(localizationService.localizeMessage("message.answer"));
            String answer = interactionService.readLine();
            if (exerciseService.checkAnswer(exercise, answer)) {
                interactionService.sendMessage(localizationService.localizeMessage("message.answer.correct"));
                user.raiseAnswersCounter();
            } else {
                interactionService.sendMessage(localizationService.localizeMessage("message.answer.wrong"));
            }
        });
        interactionService.sendMessage(localizationService.localizeMessage("message.quiz.end")
                + user.getCorrectAnswersCounter());
    }

    private void quit() {
        interactionService.sendMessage(localizationService.localizeMessage("messages.quit"));
        System.exit(-1);
    }

    private User createUser() {
        interactionService.sendMessage(localizationService.localizeMessage("message.input.key.name"));
        String name = interactionService.readLine();
        interactionService.sendMessage(localizationService.localizeMessage("message.input.key.surname"));
        String surname = interactionService.readLine();
        return new User(name, surname);
    }
}
