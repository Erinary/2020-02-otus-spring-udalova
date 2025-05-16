package ru.otus.erinary.hw07.springdatalibrary.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;

/**
 * Comment's entity.
 */
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "text", columnDefinition = "CLOB")
    private String text;

    @Column(name = "username")
    private String username;

    @Column(name = "date")
    private ZonedDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    /**
     * Default constructor.
     */
    protected Comment() {
    }

    /**
     * Creates a new {@link Comment} instance.
     *
     * @param text     content of the comment
     * @param username author of the comment
     * @param book     related book
     */
    public Comment(final String text, final String username, final Book book) {
        this.text = text;
        this.username = username;
        this.date = ZonedDateTime.now();
        this.book = book;
    }

    public @NotNull Long getId() {
        return id;
    }

    public @NotNull String getText() {
        return text;
    }

    public @NotNull String getUsername() {
        return username;
    }

    public @NotNull ZonedDateTime getDate() {
        return date;
    }

    public @NotNull Book getBook() {
        return book;
    }
}
