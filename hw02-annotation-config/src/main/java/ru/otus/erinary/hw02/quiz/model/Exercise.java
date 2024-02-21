package ru.otus.erinary.hw02.quiz.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * An exercise with answer choices.
 */
@Data
@RequiredArgsConstructor
public class Exercise {

    private final String question;

    private final List<String> responses;

    private final String answer;

}
