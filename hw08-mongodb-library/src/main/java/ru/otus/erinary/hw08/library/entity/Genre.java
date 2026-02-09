package ru.otus.erinary.hw08.library.entity;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Genre's entity.
 */
@Document(collection = "genres")
public class Genre {

    @Id
    private String id;

    @Field(name = "name")
    private String name;

    @DBRef(lazy = true)
    @Field(name = "books")
    private List<Book> books;

    /**
     * Creates a new {@link Genre} instance.
     *
     * @param name genre;s name
     */
    public Genre(final String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.books = new ArrayList<>();
    }

    @NotNull
    public String getId() {
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
