package ru.otus.erinary.studentapp.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Exercise {

    private final String question;

    private final List<String> responses;

    private final String answer;

}
