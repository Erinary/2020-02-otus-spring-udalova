package ru.otus.erinary.hw05.jdbclibrary.dao.author;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.erinary.hw05.jdbclibrary.model.Author;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import({AuthorDaoJdbc.class})
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc repository;

    @Test
    void testInsert() {
        var authors = repository.findAll();
        assertFalse(authors.isEmpty());
        assertEquals(3, authors.size());

        var author = new Author("author4");
        var id = repository.insert(author);

        authors = repository.findAll();
        assertEquals(id, author.getId());
        assertEquals(4, authors.size());
        assertTrue(authors.contains(author));
    }

    @Test
    void testUpdate() {
        var author = repository.findById(1L).orElseThrow();
        assertEquals("author1", author.getName());

        var newName = "author5";
        author.setName(newName);
        repository.update(author);

        var loadedAuthor = repository.findById(1L).orElseThrow();
        assertEquals(newName, loadedAuthor.getName());
    }

    @Test
    void testFindById() {
        var author = repository.findById(1L).orElseThrow();
        assertEquals("author1", author.getName());
        assertNull(author.getBooks());
    }

    @Test
    void testFindByName() {
        var author = repository.findByName("author1").orElseThrow();
        assertEquals(1L, author.getId());
        assertNull(author.getBooks());
    }

    @Test
    void testFindIdByName() {
        var id = repository.findIdByName("author1").orElseThrow();
        assertEquals(1L, id);
    }

    @Test
    void testFindAll() {
        var authors = repository.findAll();
        assertFalse(authors.isEmpty());
        assertEquals(3, authors.size());
        assertNull(authors.get(0).getBooks());

        var authorNames = authors.stream().map(Author::getName).collect(Collectors.toList());
        assertTrue(authorNames.containsAll(List.of("author1", "author2", "author3")));
    }

    @Test
    void testDelete() {
        var authors = repository.findAll();
        assertFalse(authors.isEmpty());
        assertEquals(3, authors.size());

        repository.delete(1L);
        authors = repository.findAll();
        assertEquals(2, authors.size());
        var authorIds = authors.stream().map(Author::getId).collect(Collectors.toList());
        assertFalse(authorIds.contains(1L));
    }
}