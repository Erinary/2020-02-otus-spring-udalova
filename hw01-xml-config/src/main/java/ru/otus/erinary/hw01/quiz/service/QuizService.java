package ru.otus.erinary.hw01.quiz.service;

import ru.otus.erinary.hw01.quiz.model.Exercise;
import ru.otus.erinary.hw01.quiz.model.User;
import ru.otus.erinary.hw01.quiz.service.exercise.ExerciseService;
import ru.otus.erinary.hw01.quiz.service.interaction.UserInteractionService;

import java.util.List;

/**
 * Сервис для запуска викторины.
 */
public final class QuizService {
    private static final String QUIZ_COMMAND = "-quiz";
    private static final String HELP_COMMAND = "-help";
    private static final String QUIT_COMMAND = "-quit";

    private final ExerciseService exerciseService;
    private final UserInteractionService userInteractionService;

    /**
     * Создает новый экземпляр {@link QuizService}.
     *
     * @param exerciseService        сервис для работы с упражнениями
     * @param userInteractionService сервис для взаимодействия с пользователем
     */
    public QuizService(final ExerciseService exerciseService, final UserInteractionService userInteractionService) {
        this.exerciseService = exerciseService;
        this.userInteractionService = userInteractionService;
    }

    /**
     * Запускает викторину.
     */
    public void start() {
        System.out.println(MessageConstants.GREETING);
        help();
        System.out.println(MessageConstants.INPUT_USER);
        var user = userInteractionService.getUser();

        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.println(MessageConstants.INPUT_COMMAND);
            String command = userInteractionService.readCommand();
            if (QUIZ_COMMAND.equals(command)) {
                quiz(user);
            } else if (QUIT_COMMAND.equals(command)) {
                quit();
            } else if (HELP_COMMAND.equals(command)) {
                help();
            } else {
                System.out.println(MessageConstants.UNKNOWN_COMMAND + command);
            }
        }
    }

    private void help() {
        System.out.println(MessageConstants.HELP);
    }

    private void quiz(final User user) {
        if (user.getCorrectAnswersCounter() != 0) {
            user.setCorrectAnswersCounter(0);
        }
        System.out.println(MessageConstants.QUIZ_START);
        List<Exercise> exercises = exerciseService.getExercises();
        exercises.forEach(exercise -> {
            System.out.println(MessageConstants.QUESTION);
            System.out.println(exercise.getQuestion());
            exercise.getResponses().forEach(System.out::println);

            System.out.println(MessageConstants.ANSWER);
            String answer = userInteractionService.readCommand();
            if (exerciseService.checkAnswer(exercise, answer)) {
                System.out.println(MessageConstants.ANSWER_CORRECT);
                user.raiseAnswersCounter();
            } else {
                System.out.println(MessageConstants.ANSWER_WRONG);
            }
        });
        System.out.println(MessageConstants.QUIZ_END + user.getCorrectAnswersCounter());
    }

    private void quit() {
        System.out.println(MessageConstants.QUIT);
        System.exit(-1);
    }
}
