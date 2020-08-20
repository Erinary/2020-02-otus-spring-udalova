package ru.otus.erinary.hw08.library.dao.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.erinary.hw08.library.dao.model.Author;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Transactional("MongoTransactionManager")
class AuthorRepositoryTest {

    private final String testAuthorId = "183accc8-e32a-11ea-87d0-0242ac130003";

    @Autowired
    private AuthorRepository repository;

    @Test
    void testSaveNew() {
        var authors = repository.findAll();
        assertFalse(authors.isEmpty());
        assertEquals(3, authors.size());

        var author = new Author("author4");
        var id = repository.save(author).getId();

        authors = repository.findAll();
        assertEquals(id, author.getId());
        assertEquals(4, authors.size());
        var authorsName = authors.stream().map(Author::getName).collect(Collectors.toList());
        assertTrue(authorsName.contains(author.getName()));
    }

    @Test
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
    void testFindIdByName() {
        var id = repository.findIdByName("author1").orElseThrow();
        assertNotNull(id);
    }

    @Test
    void testFindAll() {
        var authors = repository.findAll();
        assertFalse(authors.isEmpty());
        assertEquals(3, authors.size());
        assertNotNull(authors.get(0).getBooks());
        assertTrue(authors.get(0).getBooks().isEmpty());

        var authorNames = authors.stream().map(Author::getName).collect(Collectors.toList());
        assertTrue(authorNames.containsAll(List.of("author1", "author2", "author3")));
    }

    @Test
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