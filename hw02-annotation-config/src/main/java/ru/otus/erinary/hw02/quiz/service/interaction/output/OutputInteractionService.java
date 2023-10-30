package ru.otus.erinary.hw02.quiz.service.interaction.output;

/**
 * Интерфейс сервисов для работы с пользовательским выводом.
 */
public interface OutputInteractionService {

    /**
     * Выводит пользователю переданное сообщение.
     *
     * @param message сообщение
     */
    void sendMessage(String message);

}
