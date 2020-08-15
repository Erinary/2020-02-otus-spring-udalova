package ru.otus.erinary.hw12.library.api.controller;

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
import ru.otus.erinary.hw12.library.dao.model.Author;
import ru.otus.erinary.hw12.library.service.LibraryService;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "admin")
@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LibraryService libraryService;

    @Test
    void getAllAuthors() throws Exception {
        Mockito.when(libraryService.getAuthors())
                .thenReturn(List.of(
                        new Author(1L, "author1", Collections.emptyList()),
                        new Author(2L, "author2", Collections.emptyList()),
                        new Author(3L, "author3", Collections.emptyList())
                ));

        mvc.perform(get("/library/authors")
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("authors"))
                .andExpect(model().attribute("authors", hasSize(3)))
                .andExpect(model().attribute("authors", hasItem(hasProperty("name", is("author1")))))
                .andExpect(model().attribute("authors", hasItem(hasProperty("name", is("author2")))))
                .andExpect(model().attribute("authors", hasItem(hasProperty("name", is("author3")))));
    }

    @Test
    void getAuthor() throws Exception {
        Mockito.when(libraryService.getAuthorById(Mockito.anyLong()))
                .thenReturn(new Author(1L, "author1", Collections.emptyList()));

        mvc.perform(get("/library/author")
                .param("id", "1")
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("author-details"))
                .andExpect(model().attribute("author", allOf(
                        hasProperty("id", is(1L)),
                        hasProperty("name", is("author1"))
                )));
    }

    @Test
    void deleteAuthor() throws Exception {
        mvc.perform(post("/library/author/delete")
                .param("id", "1")
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/library/authors"));

        Mockito.verify(libraryService).deleteAuthor(Mockito.anyLong());
    }
}