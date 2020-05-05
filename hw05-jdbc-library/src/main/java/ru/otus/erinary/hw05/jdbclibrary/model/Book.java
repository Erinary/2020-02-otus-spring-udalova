package ru.otus.erinary.hw05.jdbclibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {

    private final long id;
    private final String title;
    private final int year;
    private final Author author;
    private final Genre genre;

}
