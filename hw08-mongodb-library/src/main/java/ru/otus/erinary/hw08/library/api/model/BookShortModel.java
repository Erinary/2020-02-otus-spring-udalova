package ru.otus.erinary.hw08.library.api.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A shortened version of {@link BookModel}.
 */
public class BookShortModel {

    private final String id;
    private final String title;
    private final int year;

    /**
     * A model's constructor.
     *
     * @param id    book's identifier
     * @param title book's title
     * @param year  edition year
     */
    public BookShortModel(final String id, final String title, final int year) {
        this.id = id;
        this.title = title;
        this.year = year;
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
}
