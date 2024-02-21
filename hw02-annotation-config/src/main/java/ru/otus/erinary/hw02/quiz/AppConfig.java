package ru.otus.erinary.hw02.quiz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.erinary.hw02.quiz.dao.FileExerciseLoader;
import ru.otus.erinary.hw02.quiz.service.localization.LocalizationService;
import ru.otus.erinary.hw02.quiz.dao.ExerciseLoader;
import ru.otus.erinary.hw02.quiz.service.interaction.input.InputInteractionService;
import ru.otus.erinary.hw02.quiz.service.interaction.input.ScannerService;
import ru.otus.erinary.hw02.quiz.service.interaction.output.OutputInteractionService;

import java.util.Scanner;

/**
 * Configures application context.
 */
@ComponentScan
@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    /**
     * Creates {@link MessageSource}.
     *
     * @return {@link MessageSource}
     */
    @Bean
    public MessageSource messageSource() {
        var source = new ReloadableResourceBundleMessageSource();
        source.setBasename("/messages");
        source.setDefaultEncoding("UTF-8");
        return source;
    }

    /**
     * Creates {@link InputInteractionService}.
     *
     * @param localizationService {@link LocalizationService}
     * @param outputService       {@link OutputInteractionService}
     * @return {@link InputInteractionService}
     */
    @Bean
    public InputInteractionService inputService(final LocalizationService localizationService,
                                                final OutputInteractionService outputService) {
        return new ScannerService(new Scanner(System.in), localizationService, outputService);
    }

    /**
     * Creates {@link ExerciseLoader}.
     *
     * @param fileName            basic file name with exercises
     * @param localizationService {@link LocalizationService}
     * @return {@link ExerciseLoader}
     */
    @Bean
    public ExerciseLoader exerciseLoader(final @Value("${exercise.base.file.name}") String fileName,
                                         final LocalizationService localizationService) {
        return new FileExerciseLoader(localizationService.getLocaleCode(), fileName);
    }
}
