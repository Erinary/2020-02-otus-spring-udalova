package ru.otus.erinary.hw01.quiz.service.interaction;

import ru.otus.erinary.hw01.quiz.model.User;

/**
 * Интерфейс сервисов для работы с вводом/выводом и взаимодействия с пользователем.
 */
public interface UserInteractionService {

    /**
     * Считывает команду пользователя.
     *
     * @return команда пользователя
     */
    String readCommand();

    /**
     * Формирует пользователя из пользовательского ввода.
     *
     * @return {@link User}
     */
    User getUser();

}
