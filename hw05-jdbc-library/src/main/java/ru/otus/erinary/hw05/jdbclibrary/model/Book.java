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

    public Book(final String title, final int year, final Author author, final Genre genre) {
        this.title = title;
        this.year = year;
        this.author = author;
        this.genre = genre;
    }
}
