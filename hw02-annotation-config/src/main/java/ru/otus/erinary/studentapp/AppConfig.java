package ru.otus.erinary.studentapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.erinary.studentapp.dao.ExerciseLoader;
import ru.otus.erinary.studentapp.dao.FileExerciseLoader;
import ru.otus.erinary.studentapp.service.interaction.input.InputInteractionService;
import ru.otus.erinary.studentapp.service.interaction.input.ScannerService;
import ru.otus.erinary.studentapp.service.interaction.output.OutputInteractionService;
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
    public InputInteractionService inputService(LocalizationService localizationService, OutputInteractionService outputService) {
        return new ScannerService(new Scanner(System.in), localizationService, outputService);
    }

    @Bean
    public ExerciseLoader exerciseLoader(@Value("${exercise.base.file.name}") String fileName, LocalizationService localizationService) {
        return new FileExerciseLoader(localizationService.getLocaleCode(), fileName);
    }
}
