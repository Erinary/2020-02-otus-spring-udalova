package ru.otus.erinary.hw07.springdatalibrary.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.apache.commons.collections4.ListUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Genre's entity.
 */
@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY)
    private List<Book> books;

    /**
     * Default constructor.
     */
    protected Genre() {
    }

    /**
     * Creates a new {@link Genre} instance.
     *
     * @param name genre;s name
     */
    public Genre(final String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    /**
     * Creates a new {@link Genre} instance.
     *
     * @param id    id
     * @param name  genre's name
     * @param books List of related {@link Book}
     * @implNote is used only in tests
     */
    public Genre(final Long id, final String name, final List<Book> books) {
        this.id = id;
        this.name = name;
        this.books = ListUtils.emptyIfNull(books);
    }

    @NotNull
    public Long getId() {
        return id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull final String name) {
        this.name = name;
    }

    @NotNull
    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    /**
     * Adds a book to the genre's list.
     *
     * @param book the book being added
     */
    public void addBook(@NotNull final Book book) {
        books.add(book);
    }
}
