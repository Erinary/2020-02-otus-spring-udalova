package ru.otus.erinary.hw08.library.dao.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.erinary.hw08.library.config.MongoConfig;
import ru.otus.erinary.hw08.library.dao.config.MongoTestcontainersConfig;
import ru.otus.erinary.hw08.library.dao.config.MongockConfig;
import ru.otus.erinary.hw08.library.entity.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({MongoConfig.class, MongockConfig.class})
@DataMongoTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AuthorRepositoryTest extends MongoTestcontainersConfig {

    @Autowired
    private AuthorRepository repository;

    @Test
    void testFindByName() {
        var author = repository.findByName("author1").orElseThrow();
        assertFalse(author.getBooks().isEmpty());
    }

    @Test
    void testFindAll() {
        var authors = repository.findAll();
        assertFalse(authors.isEmpty());
        assertEquals(3, authors.size());
        assertNotNull(authors.get(0).getBooks());
        assertFalse(authors.get(0).getBooks().isEmpty());

        var authorNames = authors.stream().map(Author::getName).toList();
        assertTrue(authorNames.containsAll(List.of("author1", "author2", "author3")));
    }

}