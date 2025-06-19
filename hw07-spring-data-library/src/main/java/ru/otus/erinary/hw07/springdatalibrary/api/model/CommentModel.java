package ru.otus.erinary.hw07.springdatalibrary.api.model;

import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;

/**
 * A model for displaying a comment.
 */
public class CommentModel {

    private final Long id;
    private final Long bookId;
    private final String text;
    private final String username;
    private final ZonedDateTime date;

    /**
     * A model's constructor.
     *
     * @param builder {@link Builder}
     */
    private CommentModel(final Builder builder) {
        this.id = builder.id;
        this.bookId = builder.bookId;
        this.text = builder.text;
        this.username = builder.username;
        this.date = builder.date != null ? builder.date : ZonedDateTime.now();
    }

    @NotNull
    public Long getId() {
        return id;
    }

    @NotNull
    public Long getBookId() {
        return bookId;
    }

    @NotNull
    public String getText() {
        return text;
    }

    @NotNull
    public String getUsername() {
        return username;
    }

    @NotNull
    public ZonedDateTime getDate() {
        return date;
    }

    /**
     * Creates a new {@link BookShortModel.Builder} instance.
     *
     * @return {@link BookShortModel.Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long bookId;
        private String text;
        private String username;
        private ZonedDateTime date;

        private Builder() {
        }

        public Builder setId(final Long id) {
            this.id = id;
            return this;
        }

        public Builder setBookId(final Long bookId) {
            this.bookId = bookId;
            return this;
        }

        public Builder setText(final String text) {
            this.text = text;
            return this;
        }

        public Builder setUsername(final String username) {
            this.username = username;
            return this;
        }

        public Builder setDate(final ZonedDateTime date) {
            this.date = date;
            return this;
        }

        /**
         * Creates a new {@link CommentModel} instance.
         *
         * @return {@link CommentModel}
         */
        public CommentModel build() {
            return new CommentModel(this);
        }
    }
}
