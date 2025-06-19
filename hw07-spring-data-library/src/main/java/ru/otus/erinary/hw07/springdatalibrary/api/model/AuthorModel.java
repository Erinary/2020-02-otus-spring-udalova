package ru.otus.erinary.hw07.springdatalibrary.api.model;

import org.apache.commons.collections4.ListUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * A model for displaying an author.
 */
public class AuthorModel {

    private final Long id;
    private final String name;
    private final List<BookShortModel> books;

    /**
     * A model's constructor.
     *
     * @param builder {@link BookShortModel.Builder}
     */
    private AuthorModel(final Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.books = builder.books;
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

    /**
     * Creates a new {@link Builder} instance.
     *
     * @return {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private List<BookShortModel> books;

        private Builder() {
        }

        public Builder setId(final Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(final String name) {
            this.name = name;
            return this;
        }

        public Builder setBooks(final List<BookShortModel> books) {
            this.books = ListUtils.emptyIfNull(books);
            return this;
        }

        /**
         * Creates a new {@link AuthorModel} instance.
         *
         * @return {@link AuthorModel}
         */
        public AuthorModel build() {
            return new AuthorModel(this);
        }
    }
}
