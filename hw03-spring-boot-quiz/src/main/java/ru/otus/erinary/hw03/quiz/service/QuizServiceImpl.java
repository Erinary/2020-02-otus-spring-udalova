package ru.otus.erinary.hw03.quiz.service;

import org.springframework.stereotype.Service;
import ru.otus.erinary.hw03.quiz.model.User;
import ru.otus.erinary.hw03.quiz.service.exercise.ExerciseService;
import ru.otus.erinary.hw03.quiz.service.interaction.InteractionService;

/**
 * Realization of {@link QuizService}.
 */
@Service
public class QuizServiceImpl implements QuizService {

    private static final String QUIZ_COMMAND = "-quiz";
    private static final String HELP_COMMAND = "-help";
    private static final String QUIT_COMMAND = "-quit";

    private final ExerciseService exerciseService;
    private final InteractionService interactionService;

    /**
     * Creates a new {@link QuizServiceImpl} instance.
     *
     * @param exerciseService    {@link ExerciseService}
     * @param interactionService {@link InteractionService}
     */
    public QuizServiceImpl(final ExerciseService exerciseService, final InteractionService interactionService) {
        this.exerciseService = exerciseService;
        this.interactionService = interactionService;
    }

    @Override
    public void start() {
        interactionService.sendLocalizedMessage("message.greeting");
        help();
        interactionService.sendLocalizedMessage("message.input.user");
        var user = createUser();

        //noinspection InfiniteLoopStatement
        while (true) {
            interactionService.sendLocalizedMessage("message.input.command");
            var command = interactionService.readLine();
            if (QUIZ_COMMAND.equals(command)) {
                quiz(user);
            } else if (QUIT_COMMAND.equals(command)) {
                quit();
            } else if (HELP_COMMAND.equals(command)) {
                help();
            } else {
                interactionService.sendLocalizedMessage("message.unknown.command", command);
            }
        }
    }

    private void help() {
        interactionService.sendLocalizedMessage("message.help");
    }

    private void quiz(final User user) {
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

    private void quit() {
        interactionService.sendLocalizedMessage("messages.quit");
        System.exit(-1);
    }

    private User createUser() {
        interactionService.sendLocalizedMessage("message.input.key.name");
        var name = interactionService.readLine();
        interactionService.sendLocalizedMessage("message.input.key.surname");
        var surname = interactionService.readLine();
        return new User(name, surname);
    }
}
