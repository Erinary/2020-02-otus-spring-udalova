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

    /**
     * Отправка локализованного сообщения
     *
     * @param code код сообщения
     */
    void sendLocalizedMessage(final String code);

    /**
     * Отправка локализованного сообщения
     *
     * @param code код сообщения
     * @param params параметры сообщения
     */
    void sendLocalizedMessage(final String code, final String... params);
}
