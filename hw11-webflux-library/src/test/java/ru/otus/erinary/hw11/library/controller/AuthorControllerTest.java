package ru.otus.erinary.hw11.library.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.erinary.hw11.library.api.controller.AuthorController;
import ru.otus.erinary.hw11.library.dao.model.Author;
import ru.otus.erinary.hw11.library.service.LibraryService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
        var firstId = UUID.randomUUID().toString();
        var secondId = UUID.randomUUID().toString();
        var thirdId = UUID.randomUUID().toString();

        Mockito.when(libraryService.getAuthors())
                .thenReturn(List.of(
                        new Author(firstId, "author1", Collections.emptyList()),
                        new Author(secondId, "author2", Collections.emptyList()),
                        new Author(thirdId, "author3", Collections.emptyList())
                ));

        mvc.perform(get("/library/author")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[?(@.id ==" + firstId +")].name").value("author1"))
                .andExpect(jsonPath("$[?(@.id == " + secondId +")].name").value("author2"))
                .andExpect(jsonPath("$[?(@.id == " + thirdId +")].name").value("author3"));
    }

    @Test
    void getAuthor() throws Exception {
        var id = UUID.randomUUID().toString();
        Mockito.when(libraryService.getAuthorById(id))
                .thenReturn(new Author(id, "author1", Collections.emptyList()));

        mvc.perform(get("/library/author/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("author1"));
    }

    @Test
    void deleteAuthor() throws Exception {
        mvc.perform(delete("/library/author/{id}", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Mockito.verify(libraryService).deleteAuthor(Mockito.anyString());
    }
}