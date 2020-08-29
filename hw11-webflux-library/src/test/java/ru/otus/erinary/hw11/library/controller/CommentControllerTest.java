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
import reactor.core.publisher.Mono;
import ru.otus.erinary.hw11.library.api.controller.CommentController;
import ru.otus.erinary.hw11.library.api.model.CommentDto;
import ru.otus.erinary.hw11.library.dao.model.Book;
import ru.otus.erinary.hw11.library.dao.model.Comment;
import ru.otus.erinary.hw11.library.service.LibraryService;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CommentController.class)
class CommentControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private LibraryService libraryService;

    @Test
    void saveBookComment() {
        var bookId = UUID.randomUUID().toString();
        Mockito.when(libraryService.saveCommentMono(Mockito.anyString(), Mockito.anyString(), Mockito.eq(bookId)))
                .thenAnswer(invocation ->
                        {
                            var book = new Book();
                            book.setId(invocation.getArgument(2));
                            var comment = new Comment(invocation.getArgument(0), invocation.getArgument(1), book);
                            comment.setBookId(bookId);
                            return Mono.just(comment);
                        }
                );
        var commentModel = new CommentDto(UUID.randomUUID().toString(), "example", "guest", null, bookId);

        webClient.post().uri("/library/comment/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(commentModel))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.text").isEqualTo(commentModel.getText())
                .jsonPath("$.user").isEqualTo(commentModel.getUser())
                .jsonPath("$.bookId").isEqualTo(commentModel.getBookId());
    }

    @Test
    void deleteBookComment() {
        webClient.delete().uri("/library/comment/{id}", UUID.randomUUID().toString())
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(libraryService).deleteCommentMono(Mockito.anyString());
    }
}