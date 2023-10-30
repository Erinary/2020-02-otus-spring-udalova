package ru.otus.erinary.hw02.quiz.service.interaction.input;

import ru.otus.erinary.hw02.quiz.model.User;

/**
 * Интерфейс сервисов для работы с пользовательским вводом.
 */
public interface InputInteractionService {

    /**
     * Считывает команду из пользовательского ввода.
     *
     * @return команда
     */
    String readCommand();

    /**
     * Создает нового пользователя на основе введенных пользователем данных.
     *
     * @return новый {@link User}
     */
    User getUser();

}
