package ru.otus.erinary.hw01.quiz.service.interaction;

import ru.otus.erinary.hw01.quiz.model.User;

/**
 * Интерфейс сервисов для работы с вводом/выводом и взаимодействия с пользователем
 */
public interface UserInteractionService {

    String readCommand();

    User getUser();

}
