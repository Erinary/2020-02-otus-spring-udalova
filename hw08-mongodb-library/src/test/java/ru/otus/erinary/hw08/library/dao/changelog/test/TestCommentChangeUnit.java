package ru.otus.erinary.hw08.library.dao.changelog.test;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.erinary.hw08.library.dao.repository.BookRepository;
import ru.otus.erinary.hw08.library.entity.Comment;

import java.time.ZonedDateTime;
import java.util.List;

@ChangeUnit(id = "create-test-comment-collection", order = "004", author = "Erinary")
public class TestCommentChangeUnit {

    private final MongoTemplate mongoTemplate;
    private final BookRepository bookRepository;

    public TestCommentChangeUnit(final MongoTemplate mongoTemplate,
                                 final BookRepository bookRepository) {
        this.mongoTemplate = mongoTemplate;
        this.bookRepository = bookRepository;
    }

    @Execution
    public void insertComments() {
        var firstBook = bookRepository.findByTitle("title1").orElseThrow();
        var firstComment = new Comment(
                "comment text 1",
                "user1",
                ZonedDateTime.now(),
                firstBook
        );

        var secondComment = new Comment(
                "comment text 2",
                "user2",
                ZonedDateTime.now(),
                firstBook
        );

        var thirdComment = new Comment(
                "comment text 3",
                "user3",
                ZonedDateTime.now(),
                firstBook
        );

        var secondBook = bookRepository.findByTitle("title2").orElseThrow();
        var fourthComment = new Comment(
                "comment text 4",
                "user4",
                ZonedDateTime.now(),
                secondBook
        );
        mongoTemplate.insert(List.of(firstComment, secondComment, thirdComment, fourthComment), Comment.class);
    }

    @RollbackExecution
    public void rollback() {
        mongoTemplate.dropCollection(Comment.class);
    }
}
