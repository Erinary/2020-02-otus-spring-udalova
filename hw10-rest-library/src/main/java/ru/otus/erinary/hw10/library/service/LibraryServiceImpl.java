package ru.otus.erinary.hw10.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.erinary.hw10.library.dao.repository.AuthorRepository;
import ru.otus.erinary.hw10.library.dao.repository.BookRepository;
import ru.otus.erinary.hw10.library.dao.repository.CommentRepository;
import ru.otus.erinary.hw10.library.dao.repository.GenreRepository;
import ru.otus.erinary.hw10.library.dao.model.Author;
import ru.otus.erinary.hw10.library.dao.model.Book;
import ru.otus.erinary.hw10.library.dao.model.Comment;
import ru.otus.erinary.hw10.library.dao.model.Genre;
import ru.otus.erinary.hw10.library.service.exception.LibraryServiceException;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

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
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new LibraryServiceException(String.format("Author with id [%d] does not exist", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Author getAuthorByName(final String name) {
        var author = authorRepository.findByName(name);
        author.ifPresent(a -> {
            var books = bookRepository.findAllByAuthorId(a.getId());
            a.setBooks(books);
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
    public Genre getGenreById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new LibraryServiceException(String.format("Genre with id [%d] does not exist", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Genre getGenreByName(final String name) {
        var genre = genreRepository.findByName(name);
        genre.ifPresent(g -> {
            var books = bookRepository.findAllByGenreId(g.getId());
            g.setBooks(books);
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
    public Book saveBook(Book book) {
        var author = authorRepository.findByName(book.getAuthor().getName())
                .orElseGet(() -> {
                    var a = new Author(book.getAuthor().getName());
                    authorRepository.save(a);
                    return a;
                });
        var genre = genreRepository.findByName(book.getGenre().getName())
                .orElseGet(() -> {
                    var g = new Genre(book.getGenre().getName());
                    genreRepository.save(g);
                    return g;
                });

        var savedBook = book.getId() == null ? new Book() : bookRepository
                .findById(book.getId())
                .orElseThrow(() -> new LibraryServiceException(String.format("Book with id [%d] does not exist", book.getId())));
        savedBook.setTitle(book.getTitle());
        savedBook.setYear(book.getYear());
        savedBook.setAuthor(author);
        savedBook.setGenre(genre);

        return bookRepository.save(savedBook);
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
    public Comment saveComment(String text, String user, Long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new LibraryServiceException(String.format("Book with id [%d] does not exist", bookId)));
        var comment = new Comment(text, user, book);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(final Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getBookIdByComment(Long commentId) {
        return commentRepository.findBookIdById(commentId);
    }


}
