package ru.otus.erinary.hw10.library.api.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.erinary.hw10.library.dao.model.Author;
import ru.otus.erinary.hw10.library.service.LibraryService;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        mvc.perform(get("/library/author")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[?(@.id == 1)].name").value("author1"))
                .andExpect(jsonPath("$[?(@.id == 2)].name").value("author2"))
                .andExpect(jsonPath("$[?(@.id == 3)].name").value("author3"));
    }

    @Test
    void getAuthor() throws Exception {
        Mockito.when(libraryService.getAuthorById(Mockito.anyLong()))
                .thenReturn(new Author(1L, "author1", Collections.emptyList()));

        mvc.perform(get("/library/author/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("author1"));
    }

    @Test
    void deleteAuthor() throws Exception {
        mvc.perform(delete("/library/author/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Mockito.verify(libraryService).deleteAuthor(Mockito.anyLong());
    }
}