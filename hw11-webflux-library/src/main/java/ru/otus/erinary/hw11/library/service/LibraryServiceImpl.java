package ru.otus.erinary.hw11.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.erinary.hw11.library.dao.model.Author;
import ru.otus.erinary.hw11.library.dao.model.Book;
import ru.otus.erinary.hw11.library.dao.model.Comment;
import ru.otus.erinary.hw11.library.dao.model.Genre;
import ru.otus.erinary.hw11.library.dao.repository.AuthorRepository;
import ru.otus.erinary.hw11.library.dao.repository.BookRepository;
import ru.otus.erinary.hw11.library.dao.repository.CommentRepository;
import ru.otus.erinary.hw11.library.dao.repository.GenreRepository;
import ru.otus.erinary.hw11.library.service.exception.LibraryServiceException;

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
    public Flux<Author> getAuthorsFlux() {
        return authorRepository.findAll();
    }

    @Override
    public Mono<Author> getAuthorByIdMono(final String id) {
        return authorRepository.findById(id)
                .switchIfEmpty(Mono.error(new LibraryServiceException(
                        String.format("Author with id [%s] does not exist", id))))
                .flatMap(author -> Mono.zip(
                        Mono.just(author),
                        bookRepository.findAllByAuthorId(author.getId()).collectList()
                ))
                .map(tuple -> {
                    tuple.getT1().setBooks(tuple.getT2());
                    return tuple.getT1();
                });
    }

    @Override
    public Mono<Void> deleteAuthorMono(final String id) {
        return bookRepository.saveAll(
                bookRepository.findAllByAuthorId(id)
                        .map(
                                b -> {
                                    b.setAuthor(null);
                                    return b;
                                })
        )
                .then(authorRepository.deleteById(id));
    }

    @Override
    public Flux<Genre> getGenresFlux() {
        return genreRepository.findAll();
    }

    @Override
    public Mono<Genre> getGenreByIdMono(final String id) {
        return genreRepository.findById(id)
                .switchIfEmpty(Mono.error(new LibraryServiceException(
                        String.format("Genre with id [%s] does not exist", id))));
    }

    @Override
    public Mono<Void> deleteGenreMono(final String id) {
        return bookRepository.saveAll(
                bookRepository.findAllByGenreId(id)
                        .map(
                                b -> {
                                    b.setGenre(null);
                                    return b;
                                })
        )
                .then(genreRepository.deleteById(id));
    }

    @Override
    public Flux<Book> getBooksFlux() {
        return bookRepository.findAll()
                .flatMap(this::loadBookLinks);
    }

    @Override
    public Mono<Book> getBookByIdMono(final String id) {
        return bookRepository.findById(id).flatMap(this::loadBookLinks);
    }

    private Mono<Book> loadBookLinks(Book book) {
        var toReturn = Mono.just(book);
        if (book.getAuthorId() != null) {
            toReturn = toReturn
                    .zipWith(
                            authorRepository.findById(book.getAuthorId()),
                            (theBook, author) -> {
                                theBook.setAuthor(author);
                                return theBook;
                            });
        }
        if (book.getGenreId() != null) {
            toReturn = toReturn
                    .zipWith(
                            genreRepository.findById(book.getGenreId()),
                            (theBook, genre) -> {
                                theBook.setGenre(genre);
                                return theBook;
                            });
        }
        return toReturn;
    }

    @Override
    public Mono<Book> saveBookMono(final Book book) {
        var bookMono = (book.getId() == null) ? Mono.just(new Book()) :
                bookRepository.findById(book.getId())
                        .switchIfEmpty(Mono.error(new LibraryServiceException(
                                String.format("Book with id [%s] does not exist", book.getId()))));

        var authorMono = authorRepository.findByName(book.getAuthor().getName())
                .switchIfEmpty(authorRepository.save(new Author(book.getAuthor().getName())));
        var genreMono = genreRepository.findByName(book.getGenre().getName())
                .switchIfEmpty(genreRepository.save(new Genre(book.getGenre().getName())));

        return Mono.zip(bookMono, authorMono, genreMono)
                .map(tuple -> {
                    var savedBook = tuple.getT1();
                    var author = tuple.getT2();
                    var genre = tuple.getT3();
                    savedBook.setTitle(book.getTitle());
                    savedBook.setYear(book.getYear());
                    savedBook.setAuthor(author);
                    savedBook.setGenre(genre);
                    return savedBook;
                })
                .flatMap(bookRepository::save);
    }

    @Override
    public Mono<Void> deleteBookMono(final String id) {
        var commentsFlux = commentRepository.findAllByBookId(id);
        return commentRepository.deleteAll(commentsFlux)
                .flatMap(ignored -> bookRepository.deleteById(id));
    }

    @Override
    public Flux<Comment> getBookCommentsFlux(final String bookId) {
        return commentRepository.findAllByBookId(bookId);
    }

    @Override
    public Mono<Comment> saveCommentMono(final String text, final String user, String bookId) {
        return bookRepository.findById(bookId)
                .switchIfEmpty(Mono.error(new LibraryServiceException(
                        String.format("Book with id [%s] does not exist", bookId))))
                .map(book -> new Comment(text, user, book))
                .flatMap(commentRepository::save);
    }

    @Override
    public Mono<Void> deleteCommentMono(String id) {
        return commentRepository.deleteById(id);
    }

    @Override
    public Mono<String> getBookIdByCommentMono(String commentId) {
        return commentRepository.findById(commentId)
                .map(Comment::getBook)
                .map(Book::getId)
                .switchIfEmpty(
                        Mono.error(new LibraryServiceException(String.format("Comment with id [%s] does not exist", commentId))));
    }

}
