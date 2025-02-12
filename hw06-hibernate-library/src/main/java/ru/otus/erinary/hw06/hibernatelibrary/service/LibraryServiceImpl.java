package ru.otus.erinary.hw06.hibernatelibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.erinary.hw06.hibernatelibrary.dao.author.AuthorRepository;
import ru.otus.erinary.hw06.hibernatelibrary.dao.book.BookRepository;
import ru.otus.erinary.hw06.hibernatelibrary.dao.comment.CommentRepository;
import ru.otus.erinary.hw06.hibernatelibrary.service.exception.LibraryServiceException;
import ru.otus.erinary.hw06.hibernatelibrary.dao.genre.GenreRepository;
import ru.otus.erinary.hw06.hibernatelibrary.model.Author;
import ru.otus.erinary.hw06.hibernatelibrary.model.Book;
import ru.otus.erinary.hw06.hibernatelibrary.model.Comment;
import ru.otus.erinary.hw06.hibernatelibrary.model.Genre;

import java.util.List;

/**
 * Realization of {@link LibraryService}.
 */
@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    /**
     * Creates a new {@link LibraryServiceImpl} instance.
     *
     * @param bookRepository    {@link BookRepository}
     * @param authorRepository  {@link AuthorRepository}
     * @param genreRepository   {@link GenreRepository}
     * @param commentRepository {@link CommentRepository}
     */
    @Autowired
    public LibraryServiceImpl(final BookRepository bookRepository,
                              final AuthorRepository authorRepository,
                              final GenreRepository genreRepository,
                              final CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Author getAuthorByName(final String name) {
        var author = authorRepository.findByName(name);
        author.ifPresent(a -> {
            var books = bookRepository.findAllByAuthorId(a.getId());
            books.forEach(a::addBook);
        });
        return author.orElse(null);
    }

    @Override
    @Transactional
    public void deleteAuthor(final Long id) {
        authorRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Genre getGenreByName(final String name) {
        var genre = genreRepository.findByName(name);
        genre.ifPresent(g -> {
            var books = bookRepository.findAllByGenreId(g.getId());
            books.forEach(g::addBook);
        });
        return genre.orElse(null);
    }

    @Override
    @Transactional
    public void deleteGenre(final Long id) {
        genreRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(final Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
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

        var book = id == null ? new Book() : bookRepository.findById(id)
                .orElseThrow(() -> new LibraryServiceException(String.format("Book with id [%d] does not exist", id)));

        book.setTitle(title);
        book.setYear(year);
        book.setAuthor(author);
        book.setGenre(genre);
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(final Long id) {
        bookRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getBookComments(final Long bookId) {
        return commentRepository.findAllByBookId(bookId);
    }

    @Override
    @Transactional
    public Long saveComment(final String text, final String user, final Long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new LibraryServiceException(String.format("Book with id [%d] does not exist", bookId)));
        var comment = new Comment(text, user, book);
        return commentRepository.insert(comment);
    }

    @Override
    @Transactional
    public void deleteComment(final Long id) {
        commentRepository.delete(id);
    }
}
