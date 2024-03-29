package ru.otus.erinary.hw04.quiz.service;

import org.springframework.stereotype.Service;
import ru.otus.erinary.hw04.quiz.service.exercise.ExerciseService;
import ru.otus.erinary.hw04.quiz.service.interaction.InteractionService;
import ru.otus.erinary.hw04.quiz.service.user.UserService;

/**
 * Realization of {@link QuizService}.
 */
@Service
public class QuizServiceImpl implements QuizService {

    private final ExerciseService exerciseService;
    private final InteractionService interactionService;
    private final UserService userService;

    /**
     * Creates a new {@link QuizServiceImpl} instance.
     *
     * @param exerciseService    {@link ExerciseService}
     * @param interactionService {@link InteractionService}
     * @param userService        {@link UserService}
     */
    public QuizServiceImpl(final ExerciseService exerciseService, final InteractionService interactionService,
                           final UserService userService) {
        this.exerciseService = exerciseService;
        this.interactionService = interactionService;
        this.userService = userService;
    }

    @Override
    public void help() {
        interactionService.sendLocalizedMessage("message.greeting");
        interactionService.sendLocalizedMessage("message.help");
    }

    @Override
    public void quiz() {
        var user = userService.getCurrentUser();
        if (user.getCorrectAnswersCounter() != 0) {
            user.setCorrectAnswersCounter(0);
        }
        interactionService.sendLocalizedMessage("message.quiz.start");
        var exercises = exerciseService.getExercises();
        exercises.forEach(exercise -> {
            interactionService.sendLocalizedMessage("message.question");
            interactionService.sendMessage(exercise.getQuestion());
            exercise.getResponses().forEach(System.out::println);

            interactionService.sendLocalizedMessage("message.answer");
            var answer = interactionService.readLine();
            if (exerciseService.checkAnswer(exercise, answer)) {
                interactionService.sendLocalizedMessage("message.answer.correct");
                user.raiseAnswersCounter();
            } else {
                interactionService.sendLocalizedMessage("message.answer.wrong");
            }
        });
        interactionService.sendLocalizedMessage("message.quiz.end",
                String.valueOf(user.getCorrectAnswersCounter()));
    }

    @Override
    public void quit() {
        interactionService.sendLocalizedMessage("messages.quit");
        System.exit(-1);
    }

    @Override
    public void createUser(final String name, final String surname) {
        userService.createCurrentUser(name, surname);
        interactionService.sendLocalizedMessage("message.user.created");
    }

    @Override
    public boolean checkIfUserExists() {
        return userService.isUserCreated();
    }
}
