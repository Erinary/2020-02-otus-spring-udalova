package ru.otus.erinary.hw01.quiz.service.interaction;

import ru.otus.erinary.hw01.quiz.model.User;
import ru.otus.erinary.hw01.quiz.service.MessageConstants;
import ru.otus.erinary.hw01.quiz.service.interaction.scanner.ScannerFactory;

import java.util.Scanner;

/**
 * Realization of {@link UserInteractionService} based on work with {@link Scanner}.
 */
public final class ScannerService implements UserInteractionService {

    private final Scanner scanner;

    /**
     * Creates a new {@link ScannerService} instance.
     *
     * @param factory {@link ScannerFactory}
     */
    public ScannerService(final ScannerFactory factory) {
        this.scanner = factory.createScanner();
    }

    @Override
    public String readCommand() {
        return scanner.nextLine();
    }

    @Override
    public User getUser() {
        System.out.println(MessageConstants.INPUT_NAME);
        String name = scanner.nextLine();
        System.out.println(MessageConstants.INPUT_SURNAME);
        String surname = scanner.nextLine();
        return new User(name, surname);
    }
}
