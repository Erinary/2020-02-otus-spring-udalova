package ru.otus.erinary.hw04.quiz.service.interaction;

import ru.otus.erinary.hw04.quiz.service.localization.LocalizationService;

import java.util.Scanner;

/**
 * Имплементация {@link InteractionService} для работы с консолью
 */
public class ConsoleInteractionService implements InteractionService {

    private final Scanner scanner;
    private final LocalizationService localizationService;

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
