package ru.otus.erinary.hw01.studentapp.service.interaction;

import ru.otus.erinary.hw01.studentapp.model.User;
import ru.otus.erinary.hw01.studentapp.service.MessageConstants;
import ru.otus.erinary.hw01.studentapp.service.interaction.scanner.ScannerFactory;

import java.util.Scanner;

/**
 * Сервис для работы с консолью с помощью {@link Scanner}
 */
public final class ScannerService implements UserInteractionService {

    private final Scanner scanner;

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
