package ru.otus.erinary.hw07.springdatalibrary.api.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A shortened version of {@link BookModel}.
 */
public class BookShortModel {

    private final Long id;
    private final String title;
    private final int year;

    /**
     * A model's constructor.
     *
     * @param id    book's identifier
     * @param title book's title
     * @param year  edition year
     */
    public BookShortModel(final Long id, final String title, final int year) {
        this.id = id;
        this.title = title;
        this.year = year;
    }

    @Nullable
    public Long getId() {
        return id;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }
}
