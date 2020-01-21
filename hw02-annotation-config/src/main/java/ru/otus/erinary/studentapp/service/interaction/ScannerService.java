package ru.otus.erinary.studentapp.service.interaction;

import ru.otus.erinary.studentapp.model.User;
import ru.otus.erinary.studentapp.service.localization.LocalizationService;

import java.util.Scanner;

/**
 * Сервис для работы с консолью с помощью {@link Scanner}
 */
public class ScannerService implements UserInteractionService {

    private final Scanner scanner;
    private final LocalizationService localizationService;

    public ScannerService(Scanner scanner, LocalizationService localizationService) {
        this.scanner = scanner;
        this.localizationService = localizationService;
    }

    @Override
    public String readCommand() {
        return scanner.nextLine();
    }

    @Override
    public User getUser() {
        System.out.println(localizationService.localizeMessage("message.input.key.name"));
        String name = scanner.nextLine();
        System.out.println(localizationService.localizeMessage("message.input.key.surname"));
        String surname = scanner.nextLine();
        return new User(name, surname);
    }
}
