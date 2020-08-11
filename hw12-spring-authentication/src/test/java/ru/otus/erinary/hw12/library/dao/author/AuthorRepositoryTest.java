package ru.otus.erinary.hw12.library.dao.author;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.erinary.hw12.library.model.Author;
import ru.otus.erinary.hw12.library.dao.repository.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AuthorRepositoryTest {

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
        assertTrue(authors.contains(author));
    }

    @Test
    void testSaveExisted() {
        var author = repository.findById(1L).orElseThrow();
        assertEquals("author1", author.getName());

        var newName = "author5";
        author.setName(newName);
        repository.save(author);

        var loadedAuthor = repository.findById(1L).orElseThrow();
        assertEquals(newName, loadedAuthor.getName());
    }

    @Test
    void testFindById() {
        var author = repository.findById(1L).orElseThrow();
        assertEquals("author1", author.getName());
        assertFalse(author.getBooks().isEmpty());
    }

    @Test
    void testFindByName() {
        var author = repository.findByName("author1").orElseThrow();
        assertEquals(1L, author.getId());
        assertFalse(author.getBooks().isEmpty());
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
        assertFalse(authors.get(0).getBooks().isEmpty());

        var authorNames = authors.stream().map(Author::getName).collect(Collectors.toList());
        assertTrue(authorNames.containsAll(List.of("author1", "author2", "author3")));
    }

    @Test
    void testDelete() {
        var authors = repository.findAll();
        assertFalse(authors.isEmpty());
        assertEquals(3, authors.size());

        repository.deleteById(1L);
        authors = repository.findAll();
        assertEquals(2, authors.size());
        var authorIds = authors.stream().map(Author::getId).collect(Collectors.toList());
        assertFalse(authorIds.contains(1L));
    }
}