package ru.otus.erinary.hw11.library.dao.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.erinary.hw11.library.config.MongoConfig;
import ru.otus.erinary.hw11.library.dao.model.Author;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.erinary.hw11.library.dao.changelog.test.MongockTestChangeLog.testAuthorId;

@ExtendWith(SpringExtension.class)
@Import({MongoConfig.class})
@DataMongoTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testSaveNew() {
        var authors = repository.findAll();
        assertFalse(authors.isEmpty());
        assertEquals(3, authors.size());

        var author = new Author("author4");
        var id = repository.save(author).getId();

        authors = repository.findAll();
        assertEquals(id, author.getId());
        assertEquals(4, authors.size());
        var authorsNames = authors.stream().map(Author::getName).collect(Collectors.toList());
        assertTrue(authorsNames.contains(author.getName()));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testSaveExisted() {
        var author = repository.findById(testAuthorId).orElseThrow();
        assertEquals("author1", author.getName());

        var newName = "author5";
        author.setName(newName);
        repository.save(author);

        var loadedAuthor = repository.findById(testAuthorId).orElseThrow();
        assertEquals(newName, loadedAuthor.getName());
    }

    @Test
    void testFindById() {
        var author = repository.findById(testAuthorId).orElseThrow();
        assertEquals("author1", author.getName());
        assertFalse(author.getBooks().isEmpty());
    }

    @Test
    void testFindByName() {
        var author = repository.findByName("author1").orElseThrow();
        assertEquals(testAuthorId, author.getId());
        assertFalse(author.getBooks().isEmpty());
    }

    @Test
    void testFindAll() {
        var authors = repository.findAll();
        assertFalse(authors.isEmpty());
        assertEquals(3, authors.size());
        assertNotNull(authors.get(0).getBooks());
        assertFalse(authors.get(0).getBooks().isEmpty());

        var authorNames = authors.stream().map(Author::getName).collect(Collectors.toList());
        assertTrue(authorNames.containsAll(List.of("author1", "author2", "author3")));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testDelete() {
        var authors = repository.findAll();
        assertFalse(authors.isEmpty());
        assertEquals(3, authors.size());

        var deletedAuthor = authors.iterator().next();
        repository.deleteById(deletedAuthor.getId());
        authors = repository.findAll();
        assertEquals(2, authors.size());
        var authorIds = authors.stream().map(Author::getId).collect(Collectors.toList());
        assertFalse(authorIds.contains(deletedAuthor.getId()));
    }
}