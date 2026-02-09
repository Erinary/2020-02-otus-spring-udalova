package ru.otus.erinary.hw08.library.dao.changelog;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.erinary.hw08.library.entity.Author;

import java.util.List;

@ChangeUnit(id = "create-author-collection", order = "001", author = "Erinary")
public class AuthorCollectionChangeUnit {

    private final MongoTemplate mongoTemplate;

    /**
     * Creates a new {@link AuthorCollectionChangeUnit} instance.
     *
     * @param mongoTemplate {@link MongoTemplate}
     */
    public AuthorCollectionChangeUnit(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    // CHECKSTYLE:OFF
    @Execution
    public void insertAuthors() {
        var first = new Author("Стив Макконнелл");
        var second = new Author("Анджей Сапковский");
        var third = new Author("Олдос Хаксли");

        mongoTemplate.insert(List.of(first, second, third), Author.class);
    }
    // CHECKSTYLE:ON

    // CHECKSTYLE:OFF
    @RollbackExecution
    public void rollback() {
        mongoTemplate.dropCollection(Author.class);
    }
    // CHECKSTYLE:ON
}
