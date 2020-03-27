package ru.otus.erinary.studentapp.service.interaction.input;

import ru.otus.erinary.studentapp.model.User;

/**
 * Интерфейс сервисов для работы с пользовательским вводом
 */
public interface InputInteractionService {

    String readCommand();

    User getUser();

}
