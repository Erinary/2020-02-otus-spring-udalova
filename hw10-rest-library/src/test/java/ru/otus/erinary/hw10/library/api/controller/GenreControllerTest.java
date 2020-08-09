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
import ru.otus.erinary.hw10.library.dao.model.Genre;
import ru.otus.erinary.hw10.library.service.LibraryService;

import java.util.Collections;
import java.util.List;

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
        Mockito.when(libraryService.getGenres())
                .thenReturn(List.of(
                        new Genre(1L, "genre1", Collections.emptyList()),
                        new Genre(2L, "genre2", Collections.emptyList()),
                        new Genre(3L, "genre3", Collections.emptyList())
                ));

        mvc.perform(get("/library/genre")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[?(@.id == 1)].name").value("genre1"))
                .andExpect(jsonPath("$[?(@.id == 2)].name").value("genre2"))
                .andExpect(jsonPath("$[?(@.id == 3)].name").value("genre3"));
    }

    @Test
    void getGenre() throws Exception {
        Mockito.when(libraryService.getGenreById(Mockito.anyLong()))
                .thenReturn(new Genre(1L, "genre1", Collections.emptyList()));

        mvc.perform(get("/library/genre/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("genre1"));
    }

    @Test
    void deleteGenre() throws Exception {
        mvc.perform(delete("/library/genre/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Mockito.verify(libraryService).deleteGenre(Mockito.anyLong());
    }
}