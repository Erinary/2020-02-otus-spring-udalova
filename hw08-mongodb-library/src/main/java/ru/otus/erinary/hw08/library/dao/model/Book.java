package ru.otus.erinary.hw08.library.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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

    @DBRef
    @Field(name = "author")
    private Author author;

    @DBRef
    @Field(name = "genre")
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
}
