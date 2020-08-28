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
import ru.otus.erinary.hw11.library.api.controller.GenreController;
import ru.otus.erinary.hw11.library.dao.model.Genre;
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
@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LibraryService libraryService;

    @Test
    void getAllGenres() throws Exception {
        var firstId = UUID.randomUUID().toString();
        var secondId = UUID.randomUUID().toString();
        var thirdId = UUID.randomUUID().toString();

        Mockito.when(libraryService.getGenres())
                .thenReturn(List.of(
                        new Genre(firstId, "genre1", Collections.emptyList()),
                        new Genre(secondId, "genre2", Collections.emptyList()),
                        new Genre(thirdId, "genre3", Collections.emptyList())
                ));

        mvc.perform(get("/library/genre")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[?(@.id ==" + firstId +")].name").value("genre1"))
                .andExpect(jsonPath("$[?(@.id == " + secondId +")].name").value("genre2"))
                .andExpect(jsonPath("$[?(@.id == " + thirdId +")].name").value("genre3"));
    }

    @Test
    void getGenre() throws Exception {
        var id = UUID.randomUUID().toString();
        Mockito.when(libraryService.getGenreById(id))
                .thenReturn(new Genre(id, "genre1", Collections.emptyList()));

        mvc.perform(get("/library/genre/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("genre1"));
    }

    @Test
    void deleteGenre() throws Exception {
        mvc.perform(delete("/library/genre/{id}", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Mockito.verify(libraryService).deleteGenre(Mockito.anyString());
    }
}