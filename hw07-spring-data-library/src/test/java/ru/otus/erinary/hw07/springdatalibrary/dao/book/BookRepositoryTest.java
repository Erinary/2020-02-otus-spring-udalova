package ru.otus.erinary.hw07.springdatalibrary.dao.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.erinary.hw07.springdatalibrary.dao.BookRepository;
import ru.otus.erinary.hw07.springdatalibrary.entity.Author;
import ru.otus.erinary.hw07.springdatalibrary.entity.Book;
import ru.otus.erinary.hw07.springdatalibrary.entity.Genre;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Test
    void testSaveNew() {
        var books = repository.findAll();
        assertFalse(books.isEmpty());
        assertEquals(4, books.size());

        var book = new Book();
        book.setTitle("newTitle");
        book.setYear(2018);
        book.setAuthor(new Author(2L, "author2", null));
        book.setGenre(new Genre(2L, "genre2", null));

        repository.save(book);
        assertNotEquals(0L, book.getId());

        books = repository.findAll();
        assertEquals(5, books.size());
        assertTrue(books.contains(book));
    }

    @Test
    void testSaveExisted() {
        var books = repository.findAll();
        assertEquals(4, books.size());

        var book = repository.findById(1L).orElseThrow();
        assertEquals("title1", book.getTitle());

        var newTitle = "newTitle";
        book.setTitle(newTitle);
        repository.save(book);

        var loadedBook = repository.findById(1L).orElseThrow();
        assertEquals(newTitle, loadedBook.getTitle());
    }

    @Test
    void testFindById() {
        var book = repository.findById(1L).orElseThrow();
        assertEquals("title1", book.getTitle());
        assertEquals(2020, book.getYear());
        assertEquals("author1", book.getAuthor().getName());
        assertEquals("genre1", book.getGenre().getName());
    }

    @Test
    void testFindAll() {
        var books = repository.findAll();
        assertFalse(books.isEmpty());
        assertEquals(4, books.size());
        assertNotNull(books.get(0).getAuthor());
        assertNotNull(books.get(0).getGenre());

        var bookTitles = books.stream().map(Book::getTitle).toList();
        assertTrue(bookTitles.containsAll(List.of("title1", "title2", "title3")));

        var authorNames = books.stream()
                .map(book -> book.getAuthor().getName())
                .toList();
        assertTrue(authorNames.containsAll(List.of("author1", "author2", "author3")));

        var genreNames = books.stream()
                .map(book -> book.getGenre().getName())
                .toList();
        assertTrue(genreNames.containsAll(List.of("genre1", "genre2", "genre3")));
    }

    @Test
    void testFindAllByAuthorId() {
        var books = repository.findAllByAuthorId(1L);
        assertEquals(2, books.size());
        var authorNames = books.stream()
                .map(book -> book.getAuthor().getName())
                .collect(Collectors.toSet());
        assertEquals(1, authorNames.size());
        assertTrue(authorNames.contains("author1"));
        var bookTitles = books.stream().map(Book::getTitle).toList();
        assertTrue(bookTitles.containsAll(List.of("title1", "title4")));
    }

    @Test
    void testFindAllByGenreId() {
        var books = repository.findAllByGenreId(2L);
        assertEquals(2, books.size());
        var genresNames = books.stream()
                .map(book -> book.getGenre().getName())
                .collect(Collectors.toSet());
        assertEquals(1, genresNames.size());
        assertTrue(genresNames.contains("genre2"));
        var bookTitles = books.stream().map(Book::getTitle).toList();
        assertTrue(bookTitles.containsAll(List.of("title2", "title4")));
    }

    @Test
    void testDelete() {
        var books = repository.findAll();
        assertFalse(books.isEmpty());
        assertEquals(4, books.size());

        repository.deleteById(1L);
        books = repository.findAll();
        assertEquals(3, books.size());
        var bookIds = books.stream().map(Book::getId).toList();
        assertFalse(bookIds.contains(1L));
    }

}