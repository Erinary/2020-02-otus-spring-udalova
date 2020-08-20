package ru.otus.erinary.hw08.library.dao.changelog.test;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@ChangeLog(order = "001")
public class MongockTestChangeLog {

    @ChangeSet(order = "001", id = "createTestAuthorsCollection", author = "Erinary")
    public void createAuthors(final MongoDatabase database) {
        database.createCollection("authors");
    }

    @ChangeSet(order = "002", id = "insertTestAuthorsCollection", author = "Erinary")
    public void insertAuthors(final MongoDatabase database) {
        var authorsCollection = database.getCollection("authors");
        var firstAuthor = new Document(Map.of("_id", "183accc8-e32a-11ea-87d0-0242ac130003", "name", "author1", "books", ""));
        var secondAuthor = new Document(Map.of("_id", UUID.randomUUID().toString(), "name", "author2", "books", ""));
        var thirdAuthor = new Document(Map.of("_id", UUID.randomUUID().toString(), "name", "author3", "books", ""));
        authorsCollection.insertMany(List.of(firstAuthor, secondAuthor, thirdAuthor));
    }
}
