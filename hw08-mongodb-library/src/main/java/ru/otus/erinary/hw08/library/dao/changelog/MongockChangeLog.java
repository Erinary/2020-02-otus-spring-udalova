package ru.otus.erinary.hw08.library.dao.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@ChangeLog(order = "001")
public class MongockChangeLog {

    @ChangeSet(order = "001", id = "createAuthorsCollection", author = "Erinary")
    public void createAuthors(final MongoDatabase database) {
        database.createCollection("authors");
    }

    @ChangeSet(order = "002", id = "insertAuthorsCollection", author = "Erinary")
    public void insertAuthors(final MongoDatabase database) {
        var authorsCollection = database.getCollection("authors");
        //TODO add book's ids
        var firstAuthor = new Document(Map.of("id", UUID.randomUUID(), "name", "Стив Макконнелл"));
        var secondAuthor = new Document(Map.of("id", UUID.randomUUID(), "name", "Анджей Сапковский"));
        var thirdAuthor = new Document(Map.of("id", UUID.randomUUID(), "name", "Олдос Хаксли"));
        authorsCollection.insertMany(List.of(firstAuthor, secondAuthor, thirdAuthor));
    }
}
