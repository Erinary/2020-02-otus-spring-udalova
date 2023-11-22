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
 * Component with Spring Shell commands.
 */
@ShellComponent
public class QuizCommands {

    private final QuizService quizService;
    private final LocalizationService localizationService;

    /**
     * Creates a new {@link QuizCommands} instance.
     *
     * @param quizService         {@link QuizService}
     * @param localizationService {@link LocalizationService}
     */
    @Autowired
    public QuizCommands(final QuizService quizService, final LocalizationService localizationService) {
        this.quizService = quizService;
        this.localizationService = localizationService;
    }

    /**
     * Help command.
     */
    @ShellMethod(key = {"-help", "-h"}, value = "Show quiz help.")
    public void help() {
        quizService.help();
    }

    /**
     * Creates a new user.
     *
     * @param name    user's name
     * @param surname user's surname
     */
    @ShellMethod(key = {"-user", "-u"}, value = "Create current user.")
    public void createUser(@ShellOption final String name, @ShellOption final String surname) {
        quizService.createUser(name, surname);
    }

    /**
     * Starts the quiz.
     */
    @ShellMethod(key = {"-quiz", "-qz"}, value = "Start quiz.")
    @ShellMethodAvailability(value = "checkIfUserCreated")
    public void quiz() {
        quizService.quiz();
    }

    /**
     * Starts the quiz.
     */
    @ShellMethod(key = {"-quit", "-qt"}, value = "Quit quiz.")
    public void quit() {
        quizService.quit();
    }

    @SuppressWarnings("unused")
    private Availability checkIfUserCreated() {
        return quizService.checkIfUserExists() ? Availability.available() :
                Availability.unavailable(localizationService.localizeMessage("message.user.input"));
    }
}
