package ru.otus.erinary.hw08.library.dao.changelog.test;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.erinary.hw08.library.entity.Author;

import java.util.List;

@ChangeUnit(id = "create-test-author-collection", order = "001", author = "Erinary")
public class TestAuthorChangeUnit {

    private final MongoTemplate mongoTemplate;

    public TestAuthorChangeUnit(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Execution
    public void insertAuthors() {
        var first = new Author("author1");
        var second = new Author("author2");
        var third = new Author("author3");

        mongoTemplate.insert(List.of(first, second, third), Author.class);
    }

    @RollbackExecution
    public void rollback() {
        mongoTemplate.dropCollection(Author.class);
    }
}
