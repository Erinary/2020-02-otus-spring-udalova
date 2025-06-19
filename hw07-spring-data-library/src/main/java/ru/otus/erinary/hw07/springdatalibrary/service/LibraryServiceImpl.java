package ru.otus.erinary.hw07.springdatalibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.erinary.hw07.springdatalibrary.api.model.AuthorModel;
import ru.otus.erinary.hw07.springdatalibrary.api.model.BookModel;
import ru.otus.erinary.hw07.springdatalibrary.api.model.BookShortModel;
import ru.otus.erinary.hw07.springdatalibrary.api.model.CommentModel;
import ru.otus.erinary.hw07.springdatalibrary.api.model.GenreModel;
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
import java.util.Optional;

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
    public List<AuthorModel> getAuthors() {
        return authorRepository.findAll().stream().map(author -> AuthorModel.builder()
                        .setId(author.getId())
                        .setName(author.getName())
                        .build())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorModel getAuthorByName(final String name) {
        return authorRepository.findByName(name)
                .map(author -> {
                    var bookList = author.getBooks().stream()
                            .map(book -> BookShortModel.builder()
                                    .setId(book.getId())
                                    .setTitle(book.getTitle())
                                    .setYear(book.getYear())
                                    .build())
                            .toList();
                    return AuthorModel.builder()
                            .setId(author.getId())
                            .setName(author.getName())
                            .setBooks(bookList)
                            .build();
                })
                .orElse(null);
    }

    @Override
    @Transactional
    public void deleteAuthor(final Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreModel> getGenres() {
        return genreRepository.findAll().stream().map(genre -> GenreModel.builder()
                        .setId(genre.getId())
                        .setName(genre.getName())
                        .build())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GenreModel getGenreByName(final String name) {
        return genreRepository.findByName(name)
                .map(genre -> {
                    var bookList = genre.getBooks().stream()
                            .map(book -> BookShortModel.builder()
                                    .setId(book.getId())
                                    .setTitle(book.getTitle())
                                    .setYear(book.getYear())
                                    .build())
                            .toList();
                    return GenreModel.builder()
                            .setId(genre.getId())
                            .setName(genre.getName())
                            .setBooks(bookList)
                            .build();
                })
                .orElse(null);
    }

    @Override
    @Transactional
    public void deleteGenre(final Long id) {
        genreRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookModel> getBooks() {
        return bookRepository.findAll().stream().map(book -> BookModel.builder()
                        .setId(book.getId())
                        .setTitle(book.getTitle())
                        .setYear(book.getYear())
                        .setAuthor(Optional.ofNullable(book.getAuthor()).map(Author::getName).orElse(null))
                        .setGenre(Optional.ofNullable(book.getGenre()).map(Genre::getName).orElse(null))
                        .build())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BookModel getBookById(final Long id) {
        return bookRepository.findById(id)
                .map(book -> BookModel.builder()
                        .setId(book.getId())
                        .setTitle(book.getTitle())
                        .setYear(book.getYear())
                        .setAuthor(Optional.ofNullable(book.getAuthor()).map(Author::getName).orElse(null))
                        .setGenre(Optional.ofNullable(book.getGenre()).map(Genre::getName).orElse(null))
                        .build())
                .orElse(null);
    }

    @Override
    @Transactional
    public BookModel saveBook(final BookModel model) {
        var author = authorRepository.findByName(model.getAuthor())
                .orElseGet(() -> {
                    var a = new Author(model.getAuthor());
                    authorRepository.save(a);
                    return a;
                });
        var genre = genreRepository.findByName(model.getGenre())
                .orElseGet(() -> {
                    var g = new Genre(model.getGenre());
                    genreRepository.save(g);
                    return g;
                });
        var id = model.getId();
        var book = id == null ? new Book() : bookRepository.findById(id)
                .orElseThrow(() -> new LibraryServiceException(String.format("Book with id [%d] does not exist", id)));
        book.setTitle(model.getTitle());
        book.setYear(model.getYear());
        book.setAuthor(author);
        book.setGenre(genre);

        var savedBook = bookRepository.save(book);
        return BookModel.builder()
                .setId(savedBook.getId())
                .setTitle(savedBook.getTitle())
                .setYear(savedBook.getYear())
                .setAuthor(savedBook.getAuthor().getName())
                .setGenre(savedBook.getGenre().getName())
                .build();
    }

    @Override
    @Transactional
    public void deleteBook(final Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentModel> getBookComments(final Long bookId) {
        return commentRepository.findAllByBookId(bookId).stream().map(comment -> CommentModel.builder()
                        .setId(comment.getId())
                        .setBookId(comment.getBook().getId())
                        .setText(comment.getText())
                        .setUsername(comment.getUsername())
                        .setDate(comment.getDate())
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public Long saveComment(final CommentModel model) {
        var bookId = model.getBookId();
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new LibraryServiceException(String.format("Book with id [%d] does not exist", bookId)));
        var comment = new Comment(model.getText(), model.getUsername(), model.getDate(), book);
        return commentRepository.save(comment).getId();
    }

    @Override
    @Transactional
    public void deleteComment(final Long id) {
        commentRepository.deleteById(id);
    }
}
