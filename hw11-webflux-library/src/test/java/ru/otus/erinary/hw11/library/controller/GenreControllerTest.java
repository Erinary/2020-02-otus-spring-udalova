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
import ru.otus.erinary.hw11.library.api.controller.GenreController;
import ru.otus.erinary.hw11.library.dao.model.Genre;
import ru.otus.erinary.hw11.library.service.LibraryService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = GenreController.class)
class GenreControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private LibraryService libraryService;

    @Test
    void getAllGenres() {
        var firstId = UUID.randomUUID().toString();
        var secondId = UUID.randomUUID().toString();
        var thirdId = UUID.randomUUID().toString();

        Mockito.when(libraryService.getGenres())
                .thenReturn(Flux.fromIterable(
                        List.of(
                                new Genre(firstId, "genre1", Collections.emptyList()),
                                new Genre(secondId, "genre2", Collections.emptyList()),
                                new Genre(thirdId, "genre3", Collections.emptyList())
                        )));

        webClient.get().uri("/library/genre")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[*].id").isNotEmpty()
                .jsonPath("$[?(@.id == '" + firstId + "')].name").isEqualTo("genre1")
                .jsonPath("$[?(@.id == '" + secondId + "')].name").isEqualTo("genre2")
                .jsonPath("$[?(@.id == '" + thirdId + "')].name").isEqualTo("genre3");
    }

    @Test
    void getGenre() {
        var id = UUID.randomUUID().toString();
        Mockito.when(libraryService.getGenreById(id))
                .thenReturn(Mono.just(new Genre(id, "genre1", Collections.emptyList())));

        webClient.get().uri("/library/genre/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.name").isEqualTo("genre1");
    }

    @Test
    void deleteGenre() {
        webClient.delete().uri("/library/genre/{id}", UUID.randomUUID().toString())
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(libraryService).deleteGenre(Mockito.anyString());
    }
}