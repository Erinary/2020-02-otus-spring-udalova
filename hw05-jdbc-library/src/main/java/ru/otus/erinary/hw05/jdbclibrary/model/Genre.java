package ru.otus.erinary.hw05.jdbclibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Genre's entity.
 */
@Data
@AllArgsConstructor
public class Genre {

    /**
     * Genre's id.
     */
    private Long id;

    /**
     * Genre's name.
     */
    private String name;
    /**
     * List of books, related to the genre.
     */
    private List<Book> books;

    /**
     * Creates a new {@link Genre} instance.
     *
     * @param name genre;s name
     */
    public Genre(final String name) {
        this.name = name;
    }
}
