package ru.otus.erinary.hw11.library.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "authors")
public class Author {

    @Id
    private String id;

    @Field(name = "name")
    private String name;

    @Transient
    private List<Book> books;

    public Author() {
        this.id = UUID.randomUUID().toString();
    }

    public Author(final String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }
}
