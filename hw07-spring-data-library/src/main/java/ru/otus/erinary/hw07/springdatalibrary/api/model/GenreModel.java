package ru.otus.erinary.hw07.springdatalibrary.api.model;

import org.apache.commons.collections4.ListUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * A model for displaying a genre.
 */
public class GenreModel {

    private final Long id;
    private final String name;
    private final List<BookShortModel> books;

    /**
     * A model's constructor.
     *
     * @param id    identifier of the genre
     * @param name  name of the genre
     * @param books list of related books
     */
    public GenreModel(final Long id, final String name, final List<BookShortModel> books) {
        this.id = id;
        this.name = name;
        this.books = ListUtils.emptyIfNull(books);
    }

    @NotNull
    public Long getId() {
        return id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public List<BookShortModel> getBooks() {
        return Collections.unmodifiableList(books);
    }
}
