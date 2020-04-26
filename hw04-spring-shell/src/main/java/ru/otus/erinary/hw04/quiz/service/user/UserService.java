package ru.otus.erinary.hw04.quiz.service.user;

import ru.otus.erinary.hw04.quiz.model.User;

/**
 * Сервис для работы с текущим пользователем
 */
public interface UserService {

    /**
     * Создание текущего пользователя
     *
     * @param name    имя
     * @param surname фамилия
     */
    void createCurrentUser(final String name, final String surname);

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    User getCurrentUser();

    /**
     * Проверка, создан ли уже пользователь
     *
     * @return true, если пользователь создан
     */
    boolean isUserCreated();
}
