package ru.otus.erinary.hw05.jdbclibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Author {

    private long id;
    private String name;
    private List<Book> books;

    public Author(String name) {
        this.name = name;
    }
}
