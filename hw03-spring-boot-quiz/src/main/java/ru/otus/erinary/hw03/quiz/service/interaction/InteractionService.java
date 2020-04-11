package ru.otus.erinary.hw03.quiz.service.interaction;

/**
 * Сервис взаимодействия с пользователем
 */
public interface InteractionService {

    /**
     * Чтение пользовательского ввода
     *
     * @return введенная строка
     */
    String readLine();

    /**
     * Отправка сообщения пользователю
     *
     * @param message сообщение
     */
    void sendMessage(final String message);
}
