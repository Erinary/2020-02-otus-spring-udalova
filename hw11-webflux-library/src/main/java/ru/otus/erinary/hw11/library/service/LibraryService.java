package ru.otus.erinary.hw11.library.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.erinary.hw11.library.dao.model.Author;
import ru.otus.erinary.hw11.library.dao.model.Book;
import ru.otus.erinary.hw11.library.dao.model.Comment;
import ru.otus.erinary.hw11.library.dao.model.Genre;

public interface LibraryService {

    Flux<Author> getAuthorsFlux();

    Mono<Author> getAuthorByIdMono(String id);

    Mono<Void> deleteAuthorMono(String id);

    Flux<Genre> getGenresFlux();

    Mono<Genre> getGenreByIdMono(String id);

    Mono<Void> deleteGenreMono(String id);

    Flux<Book> getBooksFlux();

    Mono<Book> getBookByIdMono(String id);

    Mono<Book> saveBookMono(Book book);

    Mono<Void> deleteBookMono(String id);

    Flux<Comment> getBookCommentsFlux(String bookId);

    Mono<Comment> saveCommentMono(String text, String user, String bookId);

    Mono<Void> deleteCommentMono(String id);

    Mono<String> getBookIdByCommentMono(String commentId);
}
