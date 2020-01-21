package ru.otus.erinary.studentapp;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.erinary.studentapp.service.interaction.UserInteractionService;
import ru.otus.erinary.studentapp.service.interaction.ScannerService;
import ru.otus.erinary.studentapp.service.localization.LocalizationService;

import java.util.Scanner;

@ComponentScan
@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("/messages");
        source.setDefaultEncoding("UTF-8");
        return source;
    }

    @Bean
    public UserInteractionService ioService(LocalizationService localizationService) {
        return new ScannerService(new Scanner(System.in), localizationService);
    }
}
