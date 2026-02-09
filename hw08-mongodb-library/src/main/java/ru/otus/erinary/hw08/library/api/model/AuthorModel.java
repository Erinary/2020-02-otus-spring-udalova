package ru.otus.erinary.hw08.library.api.model;

import org.apache.commons.collections4.ListUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * A model for displaying an author.
 */
public class AuthorModel {

    private final String id;
    private final String name;
    private final List<BookShortModel> books;

    /**
     * A model's constructor.
     *
     * @param id    author's identifier
     * @param name  author's name
     * @param books list of related books
     */
    public AuthorModel(final String id, final String name, final List<BookShortModel> books) {
        this.id = id;
        this.name = name;
        this.books = ListUtils.emptyIfNull(books);
    }

    @NotNull
    public String getId() {
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
