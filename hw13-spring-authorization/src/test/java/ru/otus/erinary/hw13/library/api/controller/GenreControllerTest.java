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
import ru.otus.erinary.hw13.library.dao.model.Genre;
import ru.otus.erinary.hw13.library.service.LibraryService;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "admin", roles = {"ADMIN"})
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

        mvc.perform(get("/library/genres")
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("genres"))
                .andExpect(model().attribute("genres", hasSize(3)))
                .andExpect(model().attribute("genres", hasItem(hasProperty("name", is("genre1")))))
                .andExpect(model().attribute("genres", hasItem(hasProperty("name", is("genre2")))))
                .andExpect(model().attribute("genres", hasItem(hasProperty("name", is("genre3")))));
    }

    @Test
    void getGenre() throws Exception {
        Mockito.when(libraryService.getGenreById(Mockito.anyLong()))
                .thenReturn(new Genre(1L, "genre1", Collections.emptyList()));

        mvc.perform(get("/library/genre")
                .param("id", "1")
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("genre-details"))
                .andExpect(model().attribute("genre", allOf(
                        hasProperty("id", is(1L)),
                        hasProperty("name", is("genre1"))
                )));
    }

    @Test
    void deleteGenre() throws Exception {
        mvc.perform(post("/library/genre/delete")
                .param("id", "1")
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/library/genres"));

        Mockito.verify(libraryService).deleteGenre(Mockito.anyLong());
    }

    @Test
    @WithMockUser("user1")
    void deleteGenreForbidden() throws Exception {
        mvc.perform(post("/library/genre/delete")
                .param("id", "1")
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isForbidden());

        Mockito.verify(libraryService, Mockito.never()).deleteGenre(Mockito.anyLong());
    }
}