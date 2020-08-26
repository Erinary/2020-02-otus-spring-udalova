package ru.otus.erinary.hw13.library.api.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.erinary.hw13.library.api.model.CommentDto;
import ru.otus.erinary.hw13.library.dao.model.Book;
import ru.otus.erinary.hw13.library.dao.model.Comment;
import ru.otus.erinary.hw13.library.dao.model.User;
import ru.otus.erinary.hw13.library.service.LibraryService;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "admin")
@ExtendWith(SpringExtension.class)
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LibraryService libraryService;

    @Test
    void saveBookCommentView() throws Exception {
        var id = 1L;
        mvc.perform(get("/library/comment/save")
                .param("id", Long.toString(id))
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("comment-form"))
                .andExpect(model().attribute("comment", hasProperty("bookId", is(id))));
    }

    @Test
    void saveBookComment() throws Exception {
        var comment = new CommentDto(1L, "example", null, null, 1L);

        Mockito.when(libraryService.saveComment(Mockito.anyString(), Mockito.anyString(), Mockito.anyLong()))
                .thenAnswer(invocation ->
                        {
                            var book = new Book();
                            book.setId(invocation.getArgument(2));
                            var user = new User(1L, invocation.getArgument(1), "pwd");
                            return new Comment(
                                    invocation.getArgument(0),
                                    user,
                                    book);
                        }
                );

        mvc.perform(post("/library/comment/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("text", comment.getText())
                .param("bookId", comment.getBookId().toString()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(String.format("/library/book?id=%d", comment.getBookId())));

        Mockito.verify(libraryService).saveComment(comment.getText(), "admin", comment.getBookId());
    }

    @Test
    void deleteBookComment() throws Exception {
        Mockito.when(libraryService.getBookIdByComment(Mockito.any()))
                .thenReturn(1L);

        mvc.perform(post("/library/comment/delete")
                .param("id", "1")
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(String.format("/library/book?id=%d", 1)));

        Mockito.verify(libraryService).deleteComment(Mockito.anyLong());
    }
}