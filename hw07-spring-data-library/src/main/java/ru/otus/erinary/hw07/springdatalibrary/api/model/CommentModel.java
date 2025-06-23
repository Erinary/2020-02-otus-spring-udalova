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
     * @param id       comment's identifier
     * @param bookId   book's identifier
     * @param text     content of the comment
     * @param username author of the comment
     * @param date     date of creation
     */
    public CommentModel(final Long id,
                        final Long bookId,
                        final String text,
                        final String username,
                        final ZonedDateTime date) {
        this.id = id;
        this.bookId = bookId;
        this.text = text;
        this.username = username;
        this.date = date != null ? date : ZonedDateTime.now();
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
}
