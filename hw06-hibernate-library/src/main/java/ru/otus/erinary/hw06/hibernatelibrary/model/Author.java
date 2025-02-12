package ru.otus.erinary.hw06.hibernatelibrary.model;

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

import java.util.List;

/**
 * Author's entity.
 */
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Book> books;

    /**
     * Default constructor.
     */
    protected Author() {
    }

    /**
     * Creates a new {@link Author} instance.
     *
     * @param name author's name
     */
    public Author(final String name) {
        this.name = name;
    }

    /**
     * Creates a new {@link Author} instance.
     *
     * @param id    id
     * @param name  author's name
     * @param books List of related {@link Book}
     * @implNote is used only in tests
     */
    public Author(final Long id, final String name, final List<Book> books) {
        this.id = id;
        this.name = name;
        this.books = books;
    }

    public @NotNull Long getId() {
        return id;
    }

    public @NotNull String getName() {
        return name;
    }

    /**
     * Changes author's name.
     *
     * @param name new name
     */
    public void rename(@NotNull final String name) {
        this.name = name;
    }

    public @NotNull List<Book> getBooks() {
        return ListUtils.emptyIfNull(books);
    }

    /**
     * Adds a book to the author's list.
     *
     * @param book the book being added
     */
    public void addBook(@NotNull final Book book) {
        books.add(book);
    }
}
