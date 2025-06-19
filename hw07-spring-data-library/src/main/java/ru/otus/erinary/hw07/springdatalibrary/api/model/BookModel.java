package ru.otus.erinary.hw07.springdatalibrary.api.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A model for displaying a book.
 */
public class BookModel {

    private final Long id;
    private final String title;
    private final int year;
    private final String author;
    private final String genre;

    /**
     * A model's constructor.
     *
     * @param builder {@link Builder}
     */
    private BookModel(final Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.year = builder.year;
        this.author = builder.author;
        this.genre = builder.genre;
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

    @Nullable
    public String getAuthor() {
        return author;
    }

    @Nullable
    public String getGenre() {
        return genre;
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
        private String title;
        private int year;
        private String author;
        private String genre;

        private Builder() {
        }

        public Builder setId(final Long id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(final String title) {
            this.title = title;
            return this;
        }

        public Builder setYear(final int year) {
            this.year = year;
            return this;
        }

        public Builder setAuthor(final String author) {
            this.author = author;
            return this;
        }

        public Builder setGenre(final String genre) {
            this.genre = genre;
            return this;
        }

        /**
         * Creates a new {@link BookModel} instance.
         *
         * @return {@link BookModel}
         */
        public BookModel build() {
            return new BookModel(this);
        }
    }
}
