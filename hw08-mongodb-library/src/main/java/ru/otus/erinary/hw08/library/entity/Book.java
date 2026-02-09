package ru.otus.erinary.hw08.library.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Document(collection = "books")
public class Book {

    @Id
    private String id;

    @Field(name = "title")
    private String title;

    @Field(name = "year")
    private int year;

    @DBRef
    @Field(name = "author")
    private Author author;

    @DBRef
    @Field(name = "genre")
    private Genre genre;

    /**
     * Default constructor.
     */
    public Book() {
        this.id = UUID.randomUUID().toString();
    }

    @NotNull
    public String getId() {
        return id;
    }

    @NotNull
    public String getTitle() {
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

    @Nullable
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(final Author author) {
        this.author = author;
    }

    @Nullable
    public Genre getGenre() {
        return genre;
    }

    public void setGenre(final Genre genre) {
        this.genre = genre;
    }
}
