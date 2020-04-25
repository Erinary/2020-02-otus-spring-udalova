package ru.otus.erinary.hw04.quiz.service.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.erinary.hw04.quiz.service.QuizService;
import ru.otus.erinary.hw04.quiz.service.localization.LocalizationService;

/**
 * Компонент с командами Spring Shell
 */
@ShellComponent
public class QuizCommands {

    private final QuizService quizService;
    private final LocalizationService localizationService;

    @Autowired
    public QuizCommands(final QuizService quizService, final LocalizationService localizationService) {
        this.quizService = quizService;
        this.localizationService = localizationService;
    }

    @ShellMethod(key = {"-help", "-h"}, value = "Show quiz help.")
    public void help() {
        quizService.help();
    }

    @ShellMethod(key = {"-user", "-u"}, value = "Create current user.")
    public String createUser(@ShellOption final String name, @ShellOption final String surname) {
        return localizationService.localizeMessage(quizService.createUser(name, surname));
    }

    @ShellMethod(key = {"-quiz", "-qz"}, value = "Start quiz.")
    @ShellMethodAvailability(value = "checkIfUserCreated")
    public void quiz() {
        quizService.quiz();
    }

    @ShellMethod(key = {"-quit", "-qt"}, value = "Quit quiz.")
    public void quit() {
        quizService.quit();
    }

    private Availability checkIfUserCreated() {
        String code = quizService.checkIfUserExists();
        return code == null ? Availability.available() : Availability.unavailable(localizationService.localizeMessage(code));
    }
}
