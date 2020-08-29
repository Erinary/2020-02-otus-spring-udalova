package ru.otus.erinary.hw11.library.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.erinary.hw11.library.api.controller.BookController;
import ru.otus.erinary.hw11.library.api.model.BookDto;
import ru.otus.erinary.hw11.library.dao.model.Author;
import ru.otus.erinary.hw11.library.dao.model.Book;
import ru.otus.erinary.hw11.library.dao.model.Comment;
import ru.otus.erinary.hw11.library.dao.model.Genre;
import ru.otus.erinary.hw11.library.service.LibraryService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = BookController.class)
class BookControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private LibraryService libraryService;

    @Test
    void getAllBooks() {
        Mockito.when(libraryService.getBooksFlux())
                .thenReturn(Flux.fromIterable(
                        List.of(
                                createBook("title1", 1970, "author1", "genre1"),
                                createBook("title2", 1980, "author1", "genre2"),
                                createBook("title3", 1990, "author2", "genre1")
                        )));

        webClient.get().uri("/library/book")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[*].id").isNotEmpty()
                .jsonPath("$[*].title").isNotEmpty()
                .jsonPath("$[*].year").isNotEmpty()
                .jsonPath("$[*].author").isNotEmpty()
                .jsonPath("$[*].genre").isNotEmpty();
    }

    @Test
    void getBook() {
        var firstBook = createBook("title1", 1970, "author", "genre");
        Mockito.when(libraryService.getBookByIdMono(firstBook.getId()))
                .thenReturn(Mono.just(firstBook));
        Mockito.when(libraryService.getBookCommentsFlux(firstBook.getId()))
                .thenReturn(Flux.fromIterable(List.of(new Comment("text", "user", firstBook))));

        webClient.get().uri("/library/book/{id}", firstBook.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(firstBook.getId())
                .jsonPath("$.title").isEqualTo(firstBook.getTitle())
                .jsonPath("$.year").isEqualTo(firstBook.getYear())
                .jsonPath("$.author").isEqualTo(firstBook.getAuthor().getName())
                .jsonPath("$.genre").isEqualTo(firstBook.getGenre().getName());

        var secondBook = createBook("title2", 1980, null, null);
        Mockito.when(libraryService.getBookByIdMono(secondBook.getId()))
                .thenReturn(Mono.just(secondBook));
        Mockito.when(libraryService.getBookCommentsFlux(secondBook.getId()))
                .thenReturn(Flux.fromIterable(List.of(new Comment("text", "user", secondBook))));

        webClient.get().uri("/library/book/{id}", secondBook.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(secondBook.getId())
                .jsonPath("$.title").isEqualTo(secondBook.getTitle())
                .jsonPath("$.year").isEqualTo(secondBook.getYear())
                .jsonPath("$.author").isEqualTo("NOT SPECIFIED")
                .jsonPath("$.genre").isEqualTo("NOT SPECIFIED");
    }

    @Test
    void saveBook() {
        Mockito.when(libraryService.saveBookMono(Mockito.any(Book.class)))
                .thenAnswer(invocation ->
                        {
                            var book = (Book) invocation.getArgument(0);
                            return Mono.just(
                                    new Book(
                                            book.getId(),
                                            book.getTitle(),
                                            book.getYear(),
                                            book.getAuthor(),
                                            book.getGenre())
                            );
                        }
                );
        var bookModel = new BookDto(UUID.randomUUID().toString(), "title", 1970, "author", "genre", null);

        webClient.post().uri("/library/book/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(bookModel))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(bookModel.getId())
                .jsonPath("$.title").isEqualTo(bookModel.getTitle())
                .jsonPath("$.year").isEqualTo(bookModel.getYear())
                .jsonPath("$.author").isEqualTo(bookModel.getAuthorName())
                .jsonPath("$.genre").isEqualTo(bookModel.getGenreName());
    }

    @Test
    void editBook() {
        Mockito.when(libraryService.saveBookMono(Mockito.any(Book.class)))
                .thenAnswer(invocation ->
                        {
                            var book = (Book) invocation.getArgument(0);
                            return Mono.just(
                                    new Book(
                                            book.getId(),
                                            book.getTitle(),
                                            book.getYear(),
                                            book.getAuthor(),
                                            book.getGenre())
                            );
                        }
                );
        Mockito.when(libraryService.getBookCommentsFlux(Mockito.anyString()))
                .thenReturn(Flux.fromIterable(List.of(new Comment("text", "user", new Book()))));
        var bookModel = new BookDto(UUID.randomUUID().toString(), "title", 1970, "author", "genre", null);

        webClient.put().uri("/library/book/{id}", bookModel.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(bookModel))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(bookModel.getId())
                .jsonPath("$.title").isEqualTo(bookModel.getTitle())
                .jsonPath("$.year").isEqualTo(bookModel.getYear())
                .jsonPath("$.author").isEqualTo(bookModel.getAuthorName())
                .jsonPath("$.genre").isEqualTo(bookModel.getGenreName());
    }

    @Test
    void deleteBook() {
        webClient.delete().uri("/library/book/{id}", UUID.randomUUID().toString())
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(libraryService).deleteBookMono(Mockito.anyString());
    }

    private Book createBook(final String title, final int year, final String authorName, final String genreName) {
        return new Book(
                UUID.randomUUID().toString(),
                title,
                year,
                authorName == null ? null : new Author(UUID.randomUUID().toString(), authorName, Collections.emptyList()),
                genreName == null ? null : new Genre(UUID.randomUUID().toString(), genreName, Collections.emptyList())
        );
    }
}