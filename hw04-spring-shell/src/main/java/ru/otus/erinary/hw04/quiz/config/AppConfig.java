package ru.otus.erinary.hw04.quiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.erinary.hw04.quiz.service.interaction.ConsoleInteractionService;
import ru.otus.erinary.hw04.quiz.service.interaction.InteractionService;
import ru.otus.erinary.hw04.quiz.service.localization.LocalizationService;

import java.util.Scanner;

@Configuration
public class AppConfig {

    @Bean
    public InteractionService interactionService(final LocalizationService localizationService) {
        return new ConsoleInteractionService(new Scanner(System.in), localizationService);
    }

}
