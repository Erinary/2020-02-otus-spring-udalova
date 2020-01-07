package ru.otus.erinary.studentapp.controller;

import org.springframework.stereotype.Controller;
import ru.otus.erinary.studentapp.model.Exercise;
import ru.otus.erinary.studentapp.model.User;
import ru.otus.erinary.studentapp.service.ExerciseService;

import java.util.List;
import java.util.Scanner;

/**
 * Контроллер для взаимодействия с пользователем через консоль
 */
@Controller
public class ExerciseController {

    private final ExerciseService exerciseService;

    private final String quizCommand = "-quiz";
    private final String helpCommand = "-help";
    private final String quitCommand = "-quit";

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    //TODO localization bundle
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Приветсвтую в программе-викторине с несложными вопросами по истории!");
        help();
        System.out.println("Введите имя: ");
        String name = scanner.nextLine();
        System.out.println("Введите фамилию: ");
        String surname = scanner.nextLine();
        User user = new User(name, surname);

        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.println("Введите команду: ");
            String command = scanner.nextLine();
            if (quizCommand.equals(command)) {
                quiz(scanner, user);
            } else if (quitCommand.equals(command)) {
                quit();
            } else if (helpCommand.equals(command)) {
                help();
            } else {
                System.out.println("Неизвестная команда: " + command);
            }
        }
    }

    private void help() {
        System.out.println(
                "Доступные команды:" + System.lineSeparator()
                        + "-help - вызов помощи" + System.lineSeparator()
                        + "-quiz - начать викторину" + System.lineSeparator()
                        + "-quit - завершение программы" + System.lineSeparator()
        );
    }

    private void quiz(Scanner scanner, User user) {
        if (user.getCorrectAnswersCounter() != 0) {
            user.setCorrectAnswersCounter(0);
        }
        System.out.println("Начинаю викторину! При выборе ответа введите номер ответа.");
        List<Exercise> exercises = exerciseService.getExercises();
        exercises.forEach(exercise -> {
            System.out.println("Вопрос: ");
            System.out.println(exercise.getQuestion());
            exercise.getResponses().forEach(System.out::println);
            System.out.println("Ответ: ");
            String answer = scanner.nextLine();
            if (exerciseService.checkAnswer(exercise, answer)) {
                System.out.println("Верно!");
                user.raiseAnswersCounter();
            } else {
                System.out.println("Неверно!");
                user.lowerAnswersCounter();
            }
        });
        System.out.println("Викторина окончена! Ваш результат: " + user.getCorrectAnswersCounter());
    }

    private void quit() {
        System.out.println("Завершаю выполнение программы");
        System.exit(-1);
    }
}
