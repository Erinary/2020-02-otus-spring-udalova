package ru.otus.erinary.hw01.quiz.service;

/**
 * Сообщения для вывода пользователю.
 */
public final class MessageConstants {

    //ExerciseController messages
    /**
     * Приветствие.
     */
    public static final String GREETING = "Приветствую в программе-викторине с несложными вопросами по истории!";

    /**
     * Сообщение ввода.
     */
    public static final String INPUT_USER = "Чтобы продолжить, введите имя и фамилию";

    /**
     * Ввод имени.
     */
    public static final String INPUT_NAME = "Имя: ";

    /**
     * Ввод фамилии.
     */
    public static final String INPUT_SURNAME = "Фамилия: ";

    /**
     * Ввод команды.
     */
    public static final String INPUT_COMMAND = "Введите команду: ";

    /**
     * Ошибка: неизвестная команда.
     */
    public static final String UNKNOWN_COMMAND = "Неизвестная команда: ";

    /**
     * Справка.
     */
    public static final String HELP = "Доступные команды:\n -help - вызов помощи\n -quiz - начать викторину\n -quit - завершение программы";

    /**
     * Правила викторины.
     */
    public static final String QUIZ_START = "Начинаю викторину! При выборе ответа введите номер ответа.";

    /**
     * Конец викторины.
     */
    public static final String QUIZ_END = "Викторина окончена! Ваш результат: ";

    /**
     * Поле вопроса.
     */
    public static final String QUESTION = "Вопрос: ";

    /**
     * Поле ответа.
     */
    public static final String ANSWER = "Ответ: ";

    /**
     * Верный ответ.
     */
    public static final String ANSWER_CORRECT = "Верно!";

    /**
     * Неверный ответ.
     */
    public static final String ANSWER_WRONG = "Неверно!";

    /**
     * Завершение программы.
     */
    public static final String QUIT = "Завершаю выполнение программы...";

}
