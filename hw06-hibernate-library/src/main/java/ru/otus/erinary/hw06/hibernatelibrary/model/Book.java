package ru.otus.erinary.hw06.hibernatelibrary.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Book's entity.
 */
@Entity
@Table(name = "books")
public class Book {

    @SuppressWarnings("unused")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "edition_year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    /**
     * Default constructor.
     */
    public Book() {
    }

    /**
     * In most cases, id is expected to be non-null. However, id might be null when a new book entity
     * is created before persisting it into the database. After that id is set by JPA.
     */
    public @Nullable Long getId() {
        return id;
    }

    public @NotNull String getTitle() {
        return title;
    }

    public void setTitle(@NotNull final String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public @NotNull Optional<Author> getAuthor() {
        return Optional.ofNullable(author);
    }

    public void setAuthor(final Author author) {
        this.author = author;
    }

    public @NotNull Optional<Genre> getGenre() {
        return Optional.ofNullable(genre);
    }

    public void setGenre(final Genre genre) {
        this.genre = genre;
    }
}
