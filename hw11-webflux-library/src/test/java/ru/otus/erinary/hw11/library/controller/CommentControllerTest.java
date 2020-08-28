package ru.otus.erinary.hw11.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.erinary.hw11.library.api.controller.CommentController;
import ru.otus.erinary.hw11.library.api.model.CommentDto;
import ru.otus.erinary.hw11.library.dao.model.Book;
import ru.otus.erinary.hw11.library.dao.model.Comment;
import ru.otus.erinary.hw11.library.service.LibraryService;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private LibraryService libraryService;

    @Test
    void saveBookComment() throws Exception {
        var bookId = UUID.randomUUID().toString();
        Mockito.when(libraryService.saveComment(Mockito.anyString(), Mockito.anyString(), bookId))
                .thenAnswer(invocation ->
                        {
                            var book = new Book();
                            book.setId(invocation.getArgument(2));
                            return new Comment(
                                    invocation.getArgument(0),
                                    invocation.getArgument(1),
                                    book);
                        }
                );
        var commentModel = new CommentDto(UUID.randomUUID().toString(), "example", "guest", null, bookId);

        mvc.perform(post("/library/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(commentModel)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value(commentModel.getText()))
                .andExpect(jsonPath("$.user").value(commentModel.getUser()))
                .andExpect(jsonPath("$.bookId").value(commentModel.getBookId()));
    }

    @Test
    void deleteBookComment() throws Exception {
        mvc.perform(delete("/library/comment/{id}", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Mockito.verify(libraryService).deleteComment(Mockito.anyString());
    }
}