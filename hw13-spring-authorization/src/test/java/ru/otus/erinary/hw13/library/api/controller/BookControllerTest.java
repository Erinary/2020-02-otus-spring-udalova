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
import ru.otus.erinary.hw13.library.dao.model.Author;
import ru.otus.erinary.hw13.library.dao.model.Book;
import ru.otus.erinary.hw13.library.dao.model.Genre;
import ru.otus.erinary.hw13.library.service.LibraryService;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "admin")
@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LibraryService libraryService;

    @Test
    void getAllBooks() throws Exception {
        Mockito.when(libraryService.getBooks())
                .thenReturn(List.of(
                        createBook("title1", 1970, "author1", "genre1"),
                        createBook("title2", 1980, "author1", "genre2"),
                        createBook("title3", 1990, "author2", "genre1")
                ));

        mvc.perform(get("/library/books")
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("books"))
                .andExpect(model().attribute("books", hasSize(3)))
                .andExpect(model().attribute("books", hasItem(hasProperty("id", notNullValue()))))
                .andExpect(model().attribute("books", hasItem(hasProperty("title", notNullValue()))))
                .andExpect(model().attribute("books", hasItem(hasProperty("year", notNullValue()))))
                .andExpect(model().attribute("books", hasItem(hasProperty("authorName", notNullValue()))))
                .andExpect(model().attribute("books", hasItem(hasProperty("genreName", notNullValue()))));
    }

    @Test
    void getBook() throws Exception {
        var firstBook = createBook("title1", 1970, "author", "genre");
        Mockito.when(libraryService.getBookById(firstBook.getId()))
                .thenReturn(firstBook);

        mvc.perform(get("/library/book")
                .param("id", firstBook.getId().toString())
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("book-details"))
                .andExpect(model().attribute("book", hasProperty("id", is(firstBook.getId()))))
                .andExpect(model().attribute("book", hasProperty("title", is(firstBook.getTitle()))))
                .andExpect(model().attribute("book", hasProperty("year", is(firstBook.getYear()))))
                .andExpect(model().attribute("book", hasProperty("authorName", is(firstBook.getAuthor().getName()))))
                .andExpect(model().attribute("book", hasProperty("genreName", is(firstBook.getGenre().getName()))));

        var secondBook = createBook("title2", 1980, null, null);
        Mockito.when(libraryService.getBookById(secondBook.getId()))
                .thenReturn(secondBook);

        mvc.perform(get("/library/book")
                .param("id", secondBook.getId().toString())
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("book-details"))
                .andExpect(model().attribute("book", hasProperty("id", is(secondBook.getId()))))
                .andExpect(model().attribute("book", hasProperty("title", is(secondBook.getTitle()))))
                .andExpect(model().attribute("book", hasProperty("year", is(secondBook.getYear()))))
                .andExpect(model().attribute("book", hasProperty("authorName", is("NOT SPECIFIED"))))
                .andExpect(model().attribute("book", hasProperty("genreName", is("NOT SPECIFIED"))));
    }

    @Test
    void saveBookView() throws Exception {
        var book = createBook("title1", 1970, "author", "genre");
        Mockito.when(libraryService.getBookById(Mockito.any()))
                .thenReturn(book);

        mvc.perform(get("/library/book/save")
                .param("id", book.getId().toString())
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("book-form"))
                .andExpect(model().attribute("book", hasProperty("id", is(book.getId()))))
                .andExpect(model().attribute("book", hasProperty("title", is(book.getTitle()))))
                .andExpect(model().attribute("book", hasProperty("year", is(book.getYear()))))
                .andExpect(model().attribute("book", hasProperty("authorName", is(book.getAuthor().getName()))))
                .andExpect(model().attribute("book", hasProperty("genreName", is(book.getGenre().getName()))));

        mvc.perform(get("/library/book/save")
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("book-form"))
                .andExpect(model().attribute("book", hasProperty("id", nullValue())))
                .andExpect(model().attribute("book", hasProperty("title", nullValue())))
                .andExpect(model().attribute("book", hasProperty("year", nullValue())))
                .andExpect(model().attribute("book", hasProperty("authorName", nullValue())))
                .andExpect(model().attribute("book", hasProperty("genreName", nullValue())));
    }

    @Test
    void saveBook() throws Exception {
        Mockito.when(libraryService.saveBook(Mockito.any(Book.class)))
                .thenAnswer(invocation ->
                        {
                            var book = (Book) invocation.getArgument(0);
                            return new Book(
                                    book.getId(),
                                    book.getTitle(),
                                    book.getYear(),
                                    book.getAuthor(),
                                    book.getGenre()
                            );
                        }
                );
        var book = createBook("title", 1970, "author", "genre");

        mvc.perform(post("/library/book/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", book.getId().toString())
                .param("title", book.getTitle())
                .param("year", Integer.toString(book.getYear()))
                .param("authorName", book.getAuthor().getName())
                .param("genreName", book.getGenre().getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("book-details"))
                .andExpect(model().attribute("book", hasProperty("id", is(book.getId()))))
                .andExpect(model().attribute("book", hasProperty("title", is(book.getTitle()))))
                .andExpect(model().attribute("book", hasProperty("year", is(book.getYear()))))
                .andExpect(model().attribute("book", hasProperty("authorName", is(book.getAuthor().getName()))))
                .andExpect(model().attribute("book", hasProperty("genreName", is(book.getGenre().getName()))));
    }

    @Test
    void deleteBook() throws Exception {
        mvc.perform(post("/library/book/delete")
                .param("id", "1")
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/library/books"));

        Mockito.verify(libraryService).deleteBook(Mockito.anyLong());
    }

    private Book createBook(final String title, final int year, final String authorName, final String genreName) {
        var random = new Random();
        return new Book(
                random.nextLong(),
                title,
                year,
                authorName == null ? null : new Author(random.nextLong(), authorName, Collections.emptyList()),
                genreName == null ? null : new Genre(random.nextLong(), genreName, Collections.emptyList())
        );
    }
}