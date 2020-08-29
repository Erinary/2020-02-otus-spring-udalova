package ru.otus.erinary.hw11.library.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.erinary.hw11.library.dao.model.Author;
import ru.otus.erinary.hw11.library.dao.model.Book;
import ru.otus.erinary.hw11.library.dao.model.Comment;
import ru.otus.erinary.hw11.library.dao.model.Genre;

public interface LibraryService {

    Flux<Author> getAuthors();

    Mono<Author> getAuthorById(String id);

    Mono<Void> deleteAuthor(String id);

    Flux<Genre> getGenres();

    Mono<Genre> getGenreById(String id);

    Mono<Void> deleteGenre(String id);

    Flux<Book> getBooks();

    Mono<Book> getBookById(String id);

    Mono<Book> saveBook(Book book);

    Mono<Void> deleteBook(String id);

    Flux<Comment> getBookComments(String bookId);

    Mono<Comment> saveComment(String text, String user, String bookId);

    Mono<Void> deleteComment(String id);

    Mono<String> getBookIdByComment(String commentId);
}
