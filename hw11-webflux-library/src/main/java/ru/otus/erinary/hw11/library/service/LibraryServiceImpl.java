package ru.otus.erinary.hw11.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.erinary.hw11.library.dao.model.Author;
import ru.otus.erinary.hw11.library.dao.model.Book;
import ru.otus.erinary.hw11.library.dao.model.Comment;
import ru.otus.erinary.hw11.library.dao.model.Genre;
import ru.otus.erinary.hw11.library.dao.repository.AuthorRepository;
import ru.otus.erinary.hw11.library.dao.repository.BookRepository;
import ru.otus.erinary.hw11.library.dao.repository.CommentRepository;
import ru.otus.erinary.hw11.library.dao.repository.GenreRepository;
import ru.otus.erinary.hw11.library.service.exception.LibraryServiceException;

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
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(String id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new LibraryServiceException(String.format("Author with id [%s] does not exist", id)));
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
    public void deleteAuthor(final String id) {
        var books = bookRepository.findAllByAuthorId(id);
        books.forEach(book -> book.setAuthor(null));
        bookRepository.saveAll(books);
        authorRepository.deleteById(id);
    }

    @Override
    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getGenreById(String id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new LibraryServiceException(String.format("Genre with id [%s] does not exist", id)));
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
    public void deleteGenre(final String id) {
        var books = bookRepository.findAllByGenreId(id);
        books.forEach(book -> book.setGenre(null));
        bookRepository.saveAll(books);
        genreRepository.deleteById(id);
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(final String id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
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
                .orElseThrow(() -> new LibraryServiceException(String.format("Book with id [%s] does not exist", book.getId())));
        savedBook.setTitle(book.getTitle());
        savedBook.setYear(book.getYear());
        savedBook.setAuthor(author);
        savedBook.setGenre(genre);

        return bookRepository.save(savedBook);
    }

    @Override
    public void deleteBook(final String id) {
        var comments = commentRepository.findAllByBookId(id);
        commentRepository.deleteAll(comments);
        bookRepository.deleteById(id);
    }

    @Override
    public List<Comment> getBookComments(final String bookId) {
        return commentRepository.findAllByBookId(bookId);
    }

    @Override
    public Comment saveComment(String text, String user, String bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new LibraryServiceException(String.format("Book with id [%s] does not exist", bookId)));
        var comment = new Comment(text, user, book);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(final String id) {
        commentRepository.deleteById(id);
    }

    @Override
    public String getBookIdByComment(String commentId) {
        return commentRepository.findById(commentId)
                .map(Comment::getBook)
                .map(Book::getId)
                .orElseThrow(() -> new LibraryServiceException(String.format("Comment with id [%s] does not exist", commentId)));
    }


}
