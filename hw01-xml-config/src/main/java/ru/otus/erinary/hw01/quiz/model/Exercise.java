package ru.otus.erinary.hw01.quiz.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Модель для упражнения с вариантами ответов.
 */
@Data
@RequiredArgsConstructor
public class Exercise {

    private final String question;

    private final List<String> responses;

    private final String answer;

}
