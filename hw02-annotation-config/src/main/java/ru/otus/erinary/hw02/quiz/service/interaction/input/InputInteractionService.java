package ru.otus.erinary.hw02.quiz.service.interaction.input;

import ru.otus.erinary.hw02.quiz.model.User;

/**
 * Интерфейс сервисов для работы с пользовательским вводом
 */
public interface InputInteractionService {

    String readCommand();

    User getUser();

}
