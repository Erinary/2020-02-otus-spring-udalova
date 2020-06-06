package ru.otus.erinary.hw06.hibernatelibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.erinary.hw06.hibernatelibrary.dao.author.AuthorRepository;
import ru.otus.erinary.hw06.hibernatelibrary.dao.book.BookRepository;
import ru.otus.erinary.hw06.hibernatelibrary.dao.genre.GenreRepository;
import ru.otus.erinary.hw06.hibernatelibrary.model.Author;
import ru.otus.erinary.hw06.hibernatelibrary.model.Book;
import ru.otus.erinary.hw06.hibernatelibrary.model.Genre;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public LibraryServiceImpl(final BookRepository bookRepository, final AuthorRepository authorRepository, final GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorByName(final String name) {
        var author = authorRepository.findByName(name);
        author.ifPresent(a -> {
            var books = bookRepository.findAllByAuthorId(a.getId());
            a.setBooks(books);
        });
        return author.orElse(null);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.delete(id);
    }

    @Override
    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getGenreByName(final String name) {
        var genre = genreRepository.findByName(name);
        genre.ifPresent(g -> {
            var books = bookRepository.findAllByGenreId(g.getId());
            g.setBooks(books);
        });
        return genre.orElse(null);
    }

    @Override
    public void deleteGenre(Long id) {
        genreRepository.delete(id);
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(final Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book saveBook(final Long id, final String title, final int year, final String authorName, final String genreName) {
        var author = authorRepository.findByName(authorName)
                .orElseGet(() -> {
                    var a = new Author(authorName);
                    authorRepository.insert(a);
                    return a;
                });
        var genre = genreRepository.findByName(genreName)
                .orElseGet(() -> {
                    var g = new Genre(genreName);
                    genreRepository.insert(g);
                    return g;
                });
        var book = new Book(id, title, year, author, genre);
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.delete(id);
    }
}
