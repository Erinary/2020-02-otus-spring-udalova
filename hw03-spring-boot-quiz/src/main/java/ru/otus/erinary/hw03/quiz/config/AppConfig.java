package ru.otus.erinary.hw03.quiz.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.erinary.hw03.quiz.service.interaction.ConsoleInteractionService;
import ru.otus.erinary.hw03.quiz.service.interaction.InteractionService;

import java.util.Scanner;

@Configuration
public class AppConfig {

    @Bean
    public InteractionService interactionService() {
        return new ConsoleInteractionService(new Scanner(System.in));
    }

    //TODO не хочет автоконфигурироваться
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("messages");
        source.setDefaultEncoding("UTF-8");
        return source;
    }

}
