package ru.otus.erinary.hw06.hibernatelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Genre {

    private Long id;
    private String name;
    private List<Book> books;

    public Genre(final String name) {
        this.name = name;
    }
}
