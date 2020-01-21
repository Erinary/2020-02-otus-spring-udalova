package ru.otus.erinary.studentapp.service.interaction;

import ru.otus.erinary.studentapp.model.User;

/**
 * Интерфейс сервисов для работы с вводом/выводом и взаимодействия с пользователем
 */
public interface UserInteractionService {

    String readCommand();

    User getUser();

}
