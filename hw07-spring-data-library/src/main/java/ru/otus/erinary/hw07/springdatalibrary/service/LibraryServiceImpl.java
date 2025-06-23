package ru.otus.erinary.hw07.springdatalibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.erinary.hw07.springdatalibrary.api.model.AuthorModel;
import ru.otus.erinary.hw07.springdatalibrary.api.model.BookModel;
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
import ru.otus.erinary.hw07.springdatalibrary.service.mapper.AuthorMapper;
import ru.otus.erinary.hw07.springdatalibrary.service.mapper.BookMapper;
import ru.otus.erinary.hw07.springdatalibrary.service.mapper.CommentMapper;
import ru.otus.erinary.hw07.springdatalibrary.service.mapper.GenreMapper;

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

    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;
    private final GenreMapper genreMapper;
    private final CommentMapper commentMapper;


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
                              final CommentRepository commentRepository,
                              final BookMapper bookMapper,
                              final AuthorMapper authorMapper,
                              final GenreMapper genreMapper,
                              final CommentMapper commentMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
        this.bookMapper = bookMapper;
        this.authorMapper = authorMapper;
        this.genreMapper = genreMapper;
        this.commentMapper = commentMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorModel> getAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::mapWithoutBooks)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorModel getAuthorByName(final String name) {
        return authorRepository.findByName(name)
                .map(authorMapper::map)
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
        return genreRepository.findAll().stream()
                .map(genreMapper::mapWithoutBooks)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GenreModel getGenreByName(final String name) {
        return genreRepository.findByName(name)
                .map(genreMapper::map)
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
        return bookRepository.findAll().stream()
                .map(bookMapper::map)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BookModel getBookById(final Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::map)
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
        return bookMapper.map(savedBook);
    }

    @Override
    @Transactional
    public void deleteBook(final Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentModel> getBookComments(final Long bookId) {
        return commentRepository.findAllByBookId(bookId).stream()
                .map(commentMapper::map)
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
