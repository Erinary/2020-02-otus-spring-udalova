package ru.otus.erinary.hw08.library.entity;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Comment's entity.
 */
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    @Field(name = "text")
    private String text;

    @Field(name = "user")
    private String username;

    @Field(name = "date")
    private ZonedDateTime date;

    @DBRef(lazy = true)
    @Field(name = "book")
    private Book book;

    /**
     * Creates a new {@link Comment} instance.
     *
     * @param text     content of the comment
     * @param username author of the comment
     * @param date     date of creation
     * @param book     related book
     */
    public Comment(final String text, final String username, final ZonedDateTime date, final Book book) {
        this.id = UUID.randomUUID().toString();
        this.text = text;
        this.username = username;
        this.date = date;
        this.book = book;
    }

    @NotNull
    public String getId() {
        return id;
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

    @NotNull
    public Book getBook() {
        return book;
    }
}
