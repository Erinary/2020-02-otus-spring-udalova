package ru.otus.erinary.hw11.library.dao.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.erinary.hw11.library.config.MongoConfig;
import ru.otus.erinary.hw11.library.dao.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.erinary.hw11.library.dao.changelog.test.MongockTestChangeLog.*;

@ExtendWith(SpringExtension.class)
@Import({MongoConfig.class})
@DataMongoTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testSaveNew() {
        var comments = repository.findAll();
        assertFalse(comments.isEmpty());
        assertEquals(4, comments.size());

        var book = bookRepository.findById(testBookId).orElseThrow();
        var comment = new Comment("text", "new_user", book);
        var id = repository.save(comment).getId();

        comments = repository.findAll();
        assertEquals(id, comment.getId());
        assertEquals(5, comments.size());
        var users = comments.stream().map(Comment::getUser).collect(Collectors.toList());
        assertTrue(users.contains(comment.getUser()));
    }

    @Test
    void testFindById() {
        var comment = repository.findById(testCommentId).orElseThrow();
        assertEquals("comment text 1", comment.getText());
        assertEquals("user1", comment.getUser());
        assertEquals(testCommentDate.withNano(0).toInstant(), comment.getDate().withNano(0).toInstant());
        assertNotNull(comment.getBook());
        assertEquals(testBookId, comment.getBook().getId());
    }

    @Test
    void testFindAll() {
        var comments = repository.findAll();
        assertFalse(comments.isEmpty());
        assertEquals(4, comments.size());
        assertNotNull(comments.get(0).getBook());

        var users = comments.stream().map(Comment::getUser).collect(Collectors.toList());
        assertTrue(users.containsAll(List.of("user1", "user2", "user3", "user4")));
    }

    @Test
    void testFindAllByBookId() {
        var comments = repository.findAllByBookId(testBookId);
        assertFalse(comments.isEmpty());
        assertEquals(3, comments.size());

        var users = comments.stream().map(Comment::getUser).collect(Collectors.toList());
        assertTrue(users.containsAll(List.of("user1", "user2", "user3")));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testDelete() {
        var comments = repository.findAll();
        assertFalse(comments.isEmpty());
        assertEquals(4, comments.size());

        repository.deleteById(testCommentId);
        comments = repository.findAll();
        assertEquals(3, comments.size());
        var authorIds = comments.stream().map(Comment::getId).collect(Collectors.toList());
        assertFalse(authorIds.contains(testCommentId));
    }
}