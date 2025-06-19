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
     * @param builder {@link Builder}
     */
    private BookShortModel(final Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.year = builder.year;
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

        /**
         * Creates a new {@link BookShortModel} instance.
         *
         * @return {@link BookShortModel}
         */
        public BookShortModel build() {
            return new BookShortModel(this);
        }
    }
}
