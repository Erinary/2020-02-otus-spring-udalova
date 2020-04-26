package ru.otus.erinary.hw04.quiz.service;

/**
 * Сервис для запуска викторины
 */
public interface QuizService {

    /**
     * Вызов справки
     */
    void help();

    /**
     * Старт викторины
     */
    void quiz();

    /**
     * Выход из викторины
     */
    void quit();

    /**
     * Создание пользователя
     * @param name имя
     * @param surname фамилия
     */
    void createUser(String name, String surname);

    /**
     * Проверка, существует ли пользователь
     * @return код сообщения с результатом проверки
     */
    boolean checkIfUserExists();
}
