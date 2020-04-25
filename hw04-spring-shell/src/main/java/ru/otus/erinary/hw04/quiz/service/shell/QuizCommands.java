package ru.otus.erinary.hw04.quiz.service.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.erinary.hw04.quiz.service.QuizService;

@ShellComponent
public class QuizCommands {

    private final QuizService quizService;

    @Autowired
    public QuizCommands(final QuizService quizService) {
        this.quizService = quizService;
    }

    @ShellMethod(key = "hello", value = "Say hello")
    public String hello(@ShellOption final String value) {
        System.out.println("out");
        return "Hello " + value;
    }

    @ShellMethod(key = {"-help", "-h"}, value = "Show quiz help.")
    public void help() {
        quizService.help();
    }

    @ShellMethod(key = {"-quiz", "-qz"}, value = "Start quiz.")
    public void quiz() {
        quizService.quiz();
    }

    @ShellMethod(key = {"-quit", "-qt"}, value = "Quit quiz.")
    public void quit() {
        quizService.quit();
    }
}
