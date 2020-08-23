package ru.otus.erinary.hw08.library.dao.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.erinary.hw08.library.config.MongoConfig;
import ru.otus.erinary.hw08.library.dao.model.Author;
import ru.otus.erinary.hw08.library.dao.model.Book;
import ru.otus.erinary.hw08.library.dao.model.Genre;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.erinary.hw08.library.dao.changelog.test.MongockTestChangeLog.*;

@ExtendWith(SpringExtension.class)
@Import({MongoConfig.class})
@DataMongoTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Test
    void testSaveNew() {
        var books = repository.findAll();
        assertFalse(books.isEmpty());
        assertEquals(4, books.size());

        var book = new Book("newTitle", 2018,
                new Author(UUID.randomUUID().toString(), "author1", Collections.emptyList()),
                new Genre(UUID.randomUUID().toString(), "genre1", Collections.emptyList()));
        repository.save(book);
        assertNotNull(book.getId());

        books = repository.findAll();
        assertEquals(5, books.size());
        var booksTitles = books.stream().map(Book::getTitle).collect(Collectors.toList());
        assertTrue(booksTitles.contains(book.getTitle()));
    }

    @Test
    void testSaveExisted() {
        var books = repository.findAll();
        assertEquals(4, books.size());

        var book = repository.findById(testBookId).orElseThrow();
        assertEquals("title1", book.getTitle());

        var newTitle = "newTitle";
        book.setTitle(newTitle);
        repository.save(book);

        var loadedBook = repository.findById(testBookId).orElseThrow();
        assertEquals(newTitle, loadedBook.getTitle());
    }

    @Test
    void testFindById() {
        var book = repository.findById(testBookId).orElseThrow();
        assertEquals("title1", book.getTitle());
        assertEquals(2020, book.getYear());

        assertNotNull(book.getAuthor());
        assertEquals("author1", book.getAuthor().getName());

        assertNotNull(book.getGenre());
        assertEquals("genre1", book.getGenre().getName());
    }

    @Test
    void testFindAll() {
        var books = repository.findAll();
        assertFalse(books.isEmpty());
        assertEquals(4, books.size());
        assertNotNull(books.get(0).getAuthor());
        assertNotNull(books.get(0).getGenre());

        var bookTitles = books.stream().map(Book::getTitle).collect(Collectors.toList());
        assertTrue(bookTitles.containsAll(List.of("title1", "title2", "title3", "title4")));

        var authorNames = books.stream().map(book -> book.getAuthor().getName()).collect(Collectors.toList());
        assertTrue(authorNames.containsAll(List.of("author1", "author2", "author3")));

        var genreNames = books.stream().map(book -> book.getGenre().getName()).collect(Collectors.toList());
        assertTrue(genreNames.containsAll(List.of("genre1", "genre2", "genre3")));
    }

    @Test
    void testFindAllByAuthorId() {
        var books = repository.findAllByAuthorId(testAuthorId);
        assertEquals(2, books.size());
        assertEquals("author1", books.get(0).getAuthor().getName());
        var bookTitles = books.stream().map(Book::getTitle).collect(Collectors.toList());
        assertTrue(bookTitles.containsAll(List.of("title1", "title4")));
    }

    @Test
    void testFindAllByGenreId() {
        var books = repository.findAllByGenreId(testGenreId);
        assertEquals(2, books.size());
        assertEquals("genre1", books.get(0).getGenre().getName());
        var bookTitles = books.stream().map(Book::getTitle).collect(Collectors.toList());
        assertTrue(bookTitles.containsAll(List.of("title1", "title4")));
    }

    @Test
    void testDelete() {
        var books = repository.findAll();
        assertFalse(books.isEmpty());
        assertEquals(4, books.size());

        repository.deleteById(testBookId);
        books = repository.findAll();
        assertEquals(3, books.size());
        var bookIds = books.stream().map(Book::getId).collect(Collectors.toList());
        assertFalse(bookIds.contains(testBookId));
    }

}