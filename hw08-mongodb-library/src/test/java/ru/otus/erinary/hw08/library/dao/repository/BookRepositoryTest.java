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
import ru.otus.erinary.hw08.library.entity.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({MongoConfig.class, MongockConfig.class})
@DataMongoTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class BookRepositoryTest extends MongoTestcontainersConfig {

    @Autowired
    private BookRepository repository;

    @SuppressWarnings("DataFlowIssue")
    @Test
    void testFindAll() {
        var books = repository.findAll();
        assertFalse(books.isEmpty());
        assertEquals(4, books.size());
        assertNotNull(books.get(0).getAuthor());
        assertNotNull(books.get(0).getGenre());

        var bookTitles = books.stream().map(Book::getTitle).toList();
        assertTrue(bookTitles.containsAll(List.of("title1", "title2", "title3", "title4")));

        var authorNames = books.stream().map(book -> book.getAuthor().getName()).toList();
        assertTrue(authorNames.containsAll(List.of("author1", "author2", "author3")));

        var genreNames = books.stream().map(book -> book.getGenre().getName()).toList();
        assertTrue(genreNames.containsAll(List.of("genre1", "genre2", "genre3")));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void testFindAllByAuthorId() {
        var book = repository.findByTitle("title1").orElseThrow();
        var books = repository.findAllByAuthorId(book.getAuthor().getId());
        assertEquals(2, books.size());
        assertEquals("author1", books.get(0).getAuthor().getName());
        var bookTitles = books.stream().map(Book::getTitle).toList();
        assertTrue(bookTitles.containsAll(List.of("title1", "title4")));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void testFindAllByGenreId() {
        var book = repository.findByTitle("title1").orElseThrow();
        var books = repository.findAllByGenreId(book.getGenre().getId());
        assertEquals(2, books.size());
        assertEquals("genre1", books.get(0).getGenre().getName());
        var bookTitles = books.stream().map(Book::getTitle).toList();
        assertTrue(bookTitles.containsAll(List.of("title1", "title4")));
    }

}