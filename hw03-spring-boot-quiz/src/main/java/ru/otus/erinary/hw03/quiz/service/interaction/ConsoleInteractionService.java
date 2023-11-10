package ru.otus.erinary.hw03.quiz.service.interaction;

import ru.otus.erinary.hw03.quiz.service.localization.LocalizationService;

import java.util.Scanner;

/**
 * Realization of {@link InteractionService} that based on work with the console.
 */
public class ConsoleInteractionService implements InteractionService {

    private final Scanner scanner;
    private final LocalizationService localizationService;

    /**
     * Creates a new {@link ConsoleInteractionService} instance.
     *
     * @param scanner             {@link Scanner}
     * @param localizationService {@link LocalizationService}
     */
    public ConsoleInteractionService(final Scanner scanner, final LocalizationService localizationService) {
        this.scanner = scanner;
        this.localizationService = localizationService;
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public void sendMessage(final String message) {
        System.out.println(message);
    }

    @Override
    public void sendLocalizedMessage(final String code) {
        System.out.println(localizationService.localizeMessage(code));
    }

    @Override
    public void sendLocalizedMessage(final String code, final String... params) {
        System.out.println(localizationService.localizeMessageWithParams(code, params));
    }
}
