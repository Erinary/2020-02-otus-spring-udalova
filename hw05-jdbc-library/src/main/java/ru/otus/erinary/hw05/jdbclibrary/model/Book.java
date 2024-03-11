package ru.otus.erinary.hw05.jdbclibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Book's entity.
 */
@Data
@AllArgsConstructor
public class Book {

    /**
     * Book's id.
     */
    private Long id;

    /**
     * Book's title.
     */
    private String title;

    /**
     * Year of the book's publication.
     */
    private int year;

    /**
     * Book's author.
     */
    private Author author;

    /**
     * Book's genre.
     */
    private Genre genre;

    /**
     * Creates a new {@link Book} instance.
     *
     * @param title  book's title
     * @param year   year of publication
     * @param author {@link Author}
     * @param genre  {@link Genre}
     */
    public Book(final String title, final int year, final Author author, final Genre genre) {
        this.title = title;
        this.year = year;
        this.author = author;
        this.genre = genre;
    }
}
