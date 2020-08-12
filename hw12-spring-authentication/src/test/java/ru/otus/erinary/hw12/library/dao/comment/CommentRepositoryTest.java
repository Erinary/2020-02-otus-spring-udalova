package ru.otus.erinary.hw12.library.dao.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.erinary.hw12.library.dao.repository.BookRepository;
import ru.otus.erinary.hw12.library.dao.model.Comment;
import ru.otus.erinary.hw12.library.dao.repository.CommentRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testSaveNew() {
        var comments = repository.findAll();
        assertFalse(comments.isEmpty());
        assertEquals(4, comments.size());

        var book = bookRepository.findById(1L).orElseThrow();
        var comment = new Comment("text", "new_user", book);
        var id = repository.save(comment).getId();

        comments = repository.findAll();
        assertEquals(id, comment.getId());
        assertEquals(5, comments.size());
        assertTrue(comments.contains(comment));
    }

    @Test
    void testFindById() {
        var comment = repository.findById(1L).orElseThrow();
        assertEquals("comment text 1", comment.getText());
        assertEquals("user1", comment.getUser());
        assertEquals(ZonedDateTime.parse("2019-06-16T10:15:30+03:00[Europe/Moscow]").toInstant(), comment.getDate().toInstant());
        assertNotNull(comment.getBook());
        assertEquals(1L, comment.getBook().getId());
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
        var comments = repository.findAllByBookId(1L);
        assertFalse(comments.isEmpty());
        assertEquals(3, comments.size());

        var users = comments.stream().map(Comment::getUser).collect(Collectors.toList());
        assertTrue(users.containsAll(List.of("user1", "user2", "user3")));
    }

    @Test
    void testDelete() {
        var comments = repository.findAll();
        assertFalse(comments.isEmpty());
        assertEquals(4, comments.size());

        repository.deleteById(1L);
        comments = repository.findAll();
        assertEquals(3, comments.size());
        var authorIds = comments.stream().map(Comment::getId).collect(Collectors.toList());
        assertFalse(authorIds.contains(1L));
    }

    @Test
    void testFindBookIdById() {
        var comment = repository.findById(1L).orElseThrow();
        assertNotNull(comment);
        assertNotNull(comment.getBook());

        var bookId = repository.findBookIdById(comment.getId());
        assertNotNull(bookId);
        assertEquals(comment.getBook().getId(), bookId);
    }
}