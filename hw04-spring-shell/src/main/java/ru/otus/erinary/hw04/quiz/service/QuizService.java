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
}
