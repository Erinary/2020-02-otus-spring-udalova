package ru.otus.erinary.hw07.springdatalibrary.service;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.erinary.hw07.springdatalibrary.dao.AuthorRepository;
import ru.otus.erinary.hw07.springdatalibrary.dao.BookRepository;
import ru.otus.erinary.hw07.springdatalibrary.dao.CommentRepository;
import ru.otus.erinary.hw07.springdatalibrary.dao.GenreRepository;
import ru.otus.erinary.hw07.springdatalibrary.entity.Author;
import ru.otus.erinary.hw07.springdatalibrary.entity.Book;
import ru.otus.erinary.hw07.springdatalibrary.entity.Comment;
import ru.otus.erinary.hw07.springdatalibrary.entity.Genre;
import ru.otus.erinary.hw07.springdatalibrary.service.exception.LibraryServiceException;

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

    @Autowired
    private EntityManager entityManager;

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
        authorRepository.deleteById(id);
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
        genreRepository.deleteById(id);
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
                    authorRepository.save(a);
                    return a;
                });
        var genre = genreRepository.findByName(genreName)
                .orElseGet(() -> {
                    var g = new Genre(genreName);
                    genreRepository.save(g);
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
        bookRepository.deleteById(id);
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
        return commentRepository.save(comment).getId();
    }

    @Override
    @Transactional
    public void deleteComment(final Long id) {
        commentRepository.deleteById(id);
    }
}
