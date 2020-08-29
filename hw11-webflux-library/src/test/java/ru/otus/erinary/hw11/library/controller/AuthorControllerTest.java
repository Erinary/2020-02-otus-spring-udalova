package ru.otus.erinary.hw11.library.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.erinary.hw11.library.api.controller.AuthorController;
import ru.otus.erinary.hw11.library.dao.model.Author;
import ru.otus.erinary.hw11.library.service.LibraryService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private LibraryService libraryService;

    @Test
    void getAllAuthors() {
        var firstId = UUID.randomUUID().toString();
        var secondId = UUID.randomUUID().toString();
        var thirdId = UUID.randomUUID().toString();

        Mockito.when(libraryService.getAuthorsFlux())
                .thenReturn(Flux.fromIterable(
                        List.of(
                                new Author(firstId, "author1", Collections.emptyList()),
                                new Author(secondId, "author2", Collections.emptyList()),
                                new Author(thirdId, "author3", Collections.emptyList())
                        )));

        webClient.get().uri("/library/author")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[*].id").isNotEmpty()
                .jsonPath("$[?(@.id == '" + firstId + "')].name").isEqualTo("author1")
                .jsonPath("$[?(@.id == '" + secondId + "')].name").isEqualTo("author2")
                .jsonPath("$[?(@.id == '" + thirdId + "')].name").isEqualTo("author3");
    }

    @Test
    void getAuthor() {
        var id = UUID.randomUUID().toString();
        Mockito.when(libraryService.getAuthorByIdMono(id))
                .thenReturn(Mono.just(new Author(id, "author1", Collections.emptyList())));

        webClient.get().uri("/library/author/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.name").isEqualTo("author1");
    }

    @Test
    void deleteAuthor() {
        webClient.delete().uri("/library/author/{id}", UUID.randomUUID().toString())
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(libraryService).deleteAuthorMono(Mockito.anyString());
    }
}