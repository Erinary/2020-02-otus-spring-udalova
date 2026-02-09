package ru.otus.erinary.hw08.library.dao.changelog;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.erinary.hw08.library.entity.Genre;

import java.util.List;

@ChangeUnit(id = "create-genre-collection", order = "002", author = "Erinary")
public class GenreCollectionChangeUnit {

    private final MongoTemplate mongoTemplate;

    /**
     * Creates a new {@link GenreCollectionChangeUnit} instance.
     *
     * @param mongoTemplate {@link MongoTemplate}
     */
    public GenreCollectionChangeUnit(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    // CHECKSTYLE:OFF
    @Execution
    public void insertGenres() {
        var first = new Genre("Программирование");
        var second = new Genre("Фэнтези");
        var third = new Genre("Классическая зарубежная проза");

        mongoTemplate.insert(List.of(first, second, third), Genre.class);
    }
    // CHECKSTYLE:ON

    // CHECKSTYLE:OFF
    @RollbackExecution
    public void rollback() {
        mongoTemplate.dropCollection(Genre.class);
    }
    // CHECKSTYLE:ON
}
