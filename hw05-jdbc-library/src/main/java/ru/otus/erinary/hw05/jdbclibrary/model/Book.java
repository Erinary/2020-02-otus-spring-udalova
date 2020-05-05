package ru.otus.erinary.hw05.jdbclibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {

    private long id;
    private String title;
    private int year;
    private Author author;
    private Genre genre;

}
