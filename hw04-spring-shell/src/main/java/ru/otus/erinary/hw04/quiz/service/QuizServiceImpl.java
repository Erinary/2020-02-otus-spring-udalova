package ru.otus.erinary.hw04.quiz.service;

import org.springframework.stereotype.Service;
import ru.otus.erinary.hw04.quiz.model.Exercise;
import ru.otus.erinary.hw04.quiz.model.User;
import ru.otus.erinary.hw04.quiz.service.exercise.ExerciseService;
import ru.otus.erinary.hw04.quiz.service.interaction.InteractionService;

import java.util.List;

/**
 * Реализация сервиса {@link QuizService}
 */
@Service
public class QuizServiceImpl implements QuizService {

    private final ExerciseService exerciseService;
    private final InteractionService interactionService;

    public QuizServiceImpl(final ExerciseService exerciseService, final InteractionService interactionService) {
        this.exerciseService = exerciseService;
        this.interactionService = interactionService;
    }

    @Override
    public void help() {
        interactionService.sendLocalizedMessage("message.greeting");
        interactionService.sendLocalizedMessage("message.help");
    }

    @Override
    public void quiz() {
        interactionService.sendLocalizedMessage("message.input.user");
        User user = createUser();
        if (user.getCorrectAnswersCounter() != 0) {
            user.setCorrectAnswersCounter(0);
        }
        interactionService.sendLocalizedMessage("message.quiz.start");
        List<Exercise> exercises = exerciseService.getExercises();
        exercises.forEach(exercise -> {
            interactionService.sendLocalizedMessage("message.question");
            interactionService.sendMessage(exercise.getQuestion());
            exercise.getResponses().forEach(System.out::println);

            interactionService.sendLocalizedMessage("message.answer");
            String answer = interactionService.readLine();
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

    private User createUser() {
        interactionService.sendLocalizedMessage("message.input.key.name");
        String name = interactionService.readLine();
        interactionService.sendLocalizedMessage("message.input.key.surname");
        String surname = interactionService.readLine();
        return new User(name, surname);
    }
}
