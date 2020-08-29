package ru.otus.erinary.hw11.library.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    @Field(name = "title")
    private String title;

    @Field(name = "year")
    private int year;

    @Field(name = "authorId")
    private String authorId;

    @Field(name = "genreId")
    private String genreId;

    @Transient
    private Author author;

    @Transient
    private Genre genre;

    public Book() {
        this.id = UUID.randomUUID().toString();
    }

    public Book(final String title, final int year, final Author author, final Genre genre) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.year = year;
        this.author = author;
        this.genre = genre;
    }

    public Book(final String id, final String title, final int year, final Author author, final Genre genre) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.author = author;
        this.genre = genre;
    }

    public void beforeConvert() {
        if (author != null) authorId = author.getId();
        if (genre != null) genreId = genre.getId();
    }
}
