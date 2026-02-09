package ru.otus.erinary.hw08.library.api.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A model for displaying a book.
 */
public class BookModel {

    private final String id;
    private final String title;
    private final int year;
    private final String author;
    private final String genre;

    /**
     * A model's constructor.
     *
     * @param id     book's identifier
     * @param title  book's title
     * @param year   edition year
     * @param author author's name
     * @param genre  name of the genre
     */
    public BookModel(final String id,
                     final String title,
                     final int year,
                     final String author,
                     final String genre) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.author = author;
        this.genre = genre;
    }

    @Nullable
    public String getId() {
        return id;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    @Nullable
    public String getAuthor() {
        return author;
    }

    @Nullable
    public String getGenre() {
        return genre;
    }
}
