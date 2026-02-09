package ru.otus.erinary.hw08.library.dao.changelog.test;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.erinary.hw08.library.entity.Genre;

import java.util.List;

@ChangeUnit(id = "create-test-genre-collection", order = "002", author = "Erinary")
public class TestGenreChangeUnit {

    private final MongoTemplate mongoTemplate;

    public TestGenreChangeUnit(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Execution
    public void insertGenres() {
        var first = new Genre("genre1");
        var second = new Genre("genre2");
        var third = new Genre("genre3");

        mongoTemplate.insert(List.of(first, second, third), Genre.class);
    }

    @RollbackExecution
    public void rollback() {
        mongoTemplate.dropCollection(Genre.class);
    }
}
