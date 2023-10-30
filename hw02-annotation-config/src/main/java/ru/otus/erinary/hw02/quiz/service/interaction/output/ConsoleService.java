package ru.otus.erinary.hw02.quiz.service.interaction.output;


import org.springframework.stereotype.Service;

/**
 * Сервис для вывода сообщений на консоль.
 */
@Service
public class ConsoleService implements OutputInteractionService {

    @Override
    public void sendMessage(final String message) {
        System.out.println(message);
    }

}
