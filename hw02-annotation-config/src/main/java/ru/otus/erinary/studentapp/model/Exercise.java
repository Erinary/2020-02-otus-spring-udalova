package ru.otus.erinary.studentapp.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Exercise {

    private final String question;

    private final String answer;

}
