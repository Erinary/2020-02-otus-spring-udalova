package ru.otus.erinary.hw05.jdbclibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.erinary.hw05.jdbclibrary.dao.author.AuthorDao;
import ru.otus.erinary.hw05.jdbclibrary.dao.book.BookDao;
import ru.otus.erinary.hw05.jdbclibrary.dao.genre.GenreDao;
import ru.otus.erinary.hw05.jdbclibrary.model.Author;
import ru.otus.erinary.hw05.jdbclibrary.model.Book;
import ru.otus.erinary.hw05.jdbclibrary.model.Genre;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Autowired
    public LibraryServiceImpl(final BookDao bookDao, final AuthorDao authorDao, final GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public List<Author> getAuthors() {
        return authorDao.findAll();
    }

    @Override
    public Author getAuthorById(final long id) {
        var author = authorDao.findById(id);
        author.ifPresent(a -> {
            var books = bookDao.findAllByAuthorId(a.getId());
            a.setBooks(books);
        });
        return author.orElse(null);
    }

    @Override
    public Author getAuthorByName(final String name) {
        var author = authorDao.findByName(name);
        author.ifPresent(a -> {
            var books = bookDao.findAllByAuthorId(a.getId());
            a.setBooks(books);
        });
        return author.orElse(null);
    }

    @Override
    public List<Genre> getGenres() {
        return genreDao.findAll();
    }

    @Override
    public Genre getGenreById(final long id) {
        var genre = genreDao.findById(id);
        genre.ifPresent(g -> {
            var books = bookDao.findAllByGenreId(g.getId());
            g.setBooks(books);
        });
        return genre.orElse(null);
    }

    @Override
    public Genre getGenreByName(final String name) {
        var genre = genreDao.findByName(name);
        genre.ifPresent(g -> {
            var books = bookDao.findAllByGenreId(g.getId());
            g.setBooks(books);
        });
        return genre.orElse(null);
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.findAll();
    }

    @Override
    public Book getBookById(final long id) {
        return null;
    }

    @Override
    public Book saveBook(final Book book) {
        return null;
    }

    @Override
    public void deleteBook(long id) {
        bookDao.delete(id);
    }
}
