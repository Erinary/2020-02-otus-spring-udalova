package ru.otus.erinary.hw04.quiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.erinary.hw04.quiz.service.interaction.ConsoleInteractionService;
import ru.otus.erinary.hw04.quiz.service.interaction.InteractionService;
import ru.otus.erinary.hw04.quiz.service.localization.LocalizationService;

import java.util.Scanner;

/**
 * Configures application context.
 */
@Configuration
public class AppConfig {

    /**
     * Returns {@link InteractionService} used for interaction with user.
     *
     * @param localizationService a {@link LocalizationService} instance
     * @return {@link InteractionService} instance
     */
    @Bean
    public InteractionService interactionService(final LocalizationService localizationService) {
        return new ConsoleInteractionService(new Scanner(System.in), localizationService);
    }

}
