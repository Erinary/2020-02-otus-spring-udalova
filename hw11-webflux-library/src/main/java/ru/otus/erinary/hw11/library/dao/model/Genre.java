package ru.otus.erinary.hw11.library.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "genres")
public class Genre {

    @Id
    private String id;

    @Field(name = "name")
    private String name;

    //TODO fix
    @DBRef(lazy = true)
    @Field(name = "books")
    private List<Book> books;

    public Genre() {
        this.id = UUID.randomUUID().toString();
    }

    public Genre(final String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }
}
