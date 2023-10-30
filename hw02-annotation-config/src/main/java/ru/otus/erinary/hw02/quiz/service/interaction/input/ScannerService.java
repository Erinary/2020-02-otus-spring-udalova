package ru.otus.erinary.hw02.quiz.service.interaction.input;

import ru.otus.erinary.hw02.quiz.model.User;
import ru.otus.erinary.hw02.quiz.service.interaction.output.OutputInteractionService;
import ru.otus.erinary.hw02.quiz.service.localization.LocalizationService;

import java.util.Scanner;

/**
 * Сервис для работы с консолью с помощью {@link Scanner}.
 */
public class ScannerService implements InputInteractionService {

    private final Scanner scanner;
    private final LocalizationService localizationService;
    private final OutputInteractionService outputService;

    /**
     * Создает новый экземпляр {@link ScannerService}.
     *
     * @param scanner             {@link Scanner}
     * @param localizationService {@link LocalizationService}
     * @param outputService       {@link  OutputInteractionService}
     */
    public ScannerService(final Scanner scanner, final LocalizationService localizationService,
                          final OutputInteractionService outputService) {
        this.scanner = scanner;
        this.localizationService = localizationService;
        this.outputService = outputService;
    }

    @Override
    public String readCommand() {
        return scanner.nextLine();
    }

    @Override
    public User getUser() {
        outputService.sendMessage(localizationService.localizeMessage("message.input.key.name"));
        String name = scanner.nextLine();
        outputService.sendMessage(localizationService.localizeMessage("message.input.key.surname"));
        String surname = scanner.nextLine();
        return new User(name, surname);
    }
}
