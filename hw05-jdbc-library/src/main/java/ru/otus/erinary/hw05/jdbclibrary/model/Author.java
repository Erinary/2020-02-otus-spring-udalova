package ru.otus.erinary.hw05.jdbclibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Author's entity.
 */
@Data
@AllArgsConstructor
public class Author {

    /**
     * Author's id.
     */
    private Long id;

    /**
     * Author's name.
     */
    private String name;

    /**
     * List of books, written by the author.
     */
    private List<Book> books;

    /**
     * Creates a new {@link Author} instance.
     *
     * @param name author's name
     */
    public Author(final String name) {
        this.name = name;
    }
}
