package ru.otus.erinary.hw08.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.erinary.hw08.library.api.model.AuthorModel;
import ru.otus.erinary.hw08.library.api.model.BookModel;
import ru.otus.erinary.hw08.library.api.model.CommentModel;
import ru.otus.erinary.hw08.library.api.model.GenreModel;
import ru.otus.erinary.hw08.library.entity.Author;
import ru.otus.erinary.hw08.library.entity.Book;
import ru.otus.erinary.hw08.library.entity.Comment;
import ru.otus.erinary.hw08.library.entity.Genre;
import ru.otus.erinary.hw08.library.dao.repository.AuthorRepository;
import ru.otus.erinary.hw08.library.dao.repository.BookRepository;
import ru.otus.erinary.hw08.library.dao.repository.CommentRepository;
import ru.otus.erinary.hw08.library.dao.repository.GenreRepository;
import ru.otus.erinary.hw08.library.service.exception.LibraryServiceException;
import ru.otus.erinary.hw08.library.service.mapper.AuthorMapper;
import ru.otus.erinary.hw08.library.service.mapper.BookMapper;
import ru.otus.erinary.hw08.library.service.mapper.CommentMapper;
import ru.otus.erinary.hw08.library.service.mapper.GenreMapper;

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
    public List<AuthorModel> getAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::mapWithoutBooks)
                .toList();
    }

    @Override
    public AuthorModel getAuthorByName(final String name) {
        var author = authorRepository.findByName(name);
        author.ifPresent(a -> {
            var books = bookRepository.findAllByAuthorId(a.getId());
            books.forEach(a::addBook);
        });
        return author.map(authorMapper::map).orElse(null);
    }

    @Override
    public void deleteAuthor(final String id) {
        var books = bookRepository.findAllByAuthorId(id);
        books.forEach(book -> book.setAuthor(null));
        bookRepository.saveAll(books);
        authorRepository.deleteById(id);
    }

    @Override
    public List<GenreModel> getGenres() {
        return genreRepository.findAll().stream()
                .map(genreMapper::mapWithoutBooks)
                .toList();
    }

    @Override
    public GenreModel getGenreByName(final String name) {
        var genre = genreRepository.findByName(name);
        genre.ifPresent(g -> {
            var books = bookRepository.findAllByGenreId(g.getId());
            books.forEach(g::addBook);
        });
        return genre.map(genreMapper::map).orElse(null);
    }

    @Override
    public void deleteGenre(final String id) {
        var books = bookRepository.findAllByGenreId(id);
        books.forEach(book -> book.setGenre(null));
        bookRepository.saveAll(books);
        genreRepository.deleteById(id);
    }

    @Override
    public List<BookModel> getBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::map)
                .toList();
    }

    @Override
    public BookModel getBookById(final String id) {
        return bookRepository.findById(id)
                .map(bookMapper::map)
                .orElse(null);
    }

    @Override
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
        var book = id == null ? new Book() : bookRepository
                .findById(id)
                .orElseThrow(() -> new LibraryServiceException(String.format("Book with id [%s] does not exist", id)));
        book.setTitle(model.getTitle());
        book.setYear(model.getYear());
        book.setAuthor(author);
        book.setGenre(genre);

        var savedBook = bookRepository.save(book);
        return bookMapper.map(savedBook);
    }

    @Override
    public void deleteBook(final String id) {
        var comments = commentRepository.findAllByBookId(id);
        commentRepository.deleteAll(comments);
        bookRepository.deleteById(id);
    }

    @Override
    public List<CommentModel> getBookComments(final String bookId) {
        return commentRepository.findAllByBookId(bookId).stream()
                .map(commentMapper::map)
                .toList();
    }

    @Override
    public String saveComment(final CommentModel model) {
        var bookId = model.getBookId();
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new LibraryServiceException(String.format("Book with id [%s] does not exist", bookId)));
        var comment = new Comment(model.getText(), model.getUsername(), model.getDate(), book);
        return commentRepository.save(comment).getId();
    }

    @Override
    public void deleteComment(final String id) {
        commentRepository.deleteById(id);
    }
}
