package ru.otus.erinary.hw03.quiz.service.interaction;

import java.util.Scanner;

/**
 * Имплементация {@link InteractionService} для работы с консолью
 */
public class ConsoleInteractionService implements InteractionService {

    private final Scanner scanner;

    public ConsoleInteractionService(final Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public void sendMessage(final String message) {
        System.out.println(message);
    }
}
