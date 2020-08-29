package ru.otus.erinary.hw11.library.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    @Field(name = "text")
    private String text;

    @Field(name = "user")
    private String user;

    @Field(name = "date")
    private ZonedDateTime date;

    @Field(name = "bookId")
    private String bookId;

    @Transient
    private Book book;

    public Comment() {
        this.id = UUID.randomUUID().toString();
    }

    public Comment(final String text, final String user, final Book book) {
        this.id = UUID.randomUUID().toString();
        this.text = text;
        this.user = user;
        this.date = ZonedDateTime.now();
        this.book = book;
    }

    public void beforeConvert() {
        bookId = book.getId();
    }
}
