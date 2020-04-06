package ru.otus.erinary.hw03.quiz.service.interaction.input;


import org.springframework.stereotype.Service;
import ru.otus.erinary.hw03.quiz.model.User;
import ru.otus.erinary.hw03.quiz.service.interaction.output.OutputInteractionService;
import ru.otus.erinary.hw03.quiz.service.localization.LocalizationService;

import java.util.Scanner;

/**
 * Сервис для работы с консолью с помощью {@link Scanner}
 */
@Service
public class ScannerService implements InputInteractionService {

    private final Scanner scanner;
    private final LocalizationService localizationService;
    private final OutputInteractionService outputService;

    public ScannerService(final LocalizationService localizationService, final OutputInteractionService outputService) {
        this.scanner = new Scanner(System.in);
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
