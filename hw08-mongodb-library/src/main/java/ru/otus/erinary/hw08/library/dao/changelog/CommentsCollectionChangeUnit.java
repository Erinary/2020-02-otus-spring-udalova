package ru.otus.erinary.hw08.library.dao.changelog;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.erinary.hw08.library.dao.repository.BookRepository;
import ru.otus.erinary.hw08.library.entity.Comment;

import java.time.ZonedDateTime;
import java.util.List;

@ChangeUnit(id = "create-comments-collection", order = "004", author = "Erinary")
public class CommentsCollectionChangeUnit {

    private final MongoTemplate mongoTemplate;
    private final BookRepository bookRepository;

    /**
     * Creates a new {@link CommentsCollectionChangeUnit} instance.
     *
     * @param mongoTemplate  {@link MongoTemplate}
     * @param bookRepository {@link BookRepository}
     */
    public CommentsCollectionChangeUnit(final MongoTemplate mongoTemplate,
                                        final BookRepository bookRepository) {
        this.mongoTemplate = mongoTemplate;
        this.bookRepository = bookRepository;
    }

    // CHECKSTYLE:OFF
    @Execution
    public void insertComments() {
        var firstBook = bookRepository.findByTitle("Совершенный код").orElseThrow();

        var firstComment = new Comment(
                "Книга содержит много отличных советов и рекомендаций, которые действительно помогают писать код лучше.",
                "Ваня",
                ZonedDateTime.now(),
                firstBook
        );

        var secondComment = new Comment(
                "Отличная книга для понимания многих важных вещей в мире программирования.",
                "Юрий",
                ZonedDateTime.now(),
                firstBook
        );

        var thirdComment = new Comment(
                "Отличная книга, можно сказать, классика. Всем рекомендую.",
                "Максим",
                ZonedDateTime.now(),
                firstBook
        );

        var secondBook = bookRepository.findByTitle("О дивный новый мир").orElseThrow();
        var fourthComment = new Comment(
                "Остроумная, абсурдная антиутопия служит замечательной иллюстрацией доведенного до крайности общества потребления.",
                "Фатима",
                ZonedDateTime.now(),
                secondBook
        );
        mongoTemplate.insert(List.of(firstComment, secondComment, thirdComment, fourthComment), Comment.class);

    }
    // CHECKSTYLE:ON

    // CHECKSTYLE:OFF
    @RollbackExecution
    public void rollback() {
        mongoTemplate.dropCollection(Comment.class);
    }
    // CHECKSTYLE:ON
}
