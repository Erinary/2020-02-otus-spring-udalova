package ru.otus.erinary.hw10.library.api.controller;

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
import ru.otus.erinary.hw10.library.api.model.BookDto;
import ru.otus.erinary.hw10.library.dao.model.Author;
import ru.otus.erinary.hw10.library.dao.model.Book;
import ru.otus.erinary.hw10.library.dao.model.Comment;
import ru.otus.erinary.hw10.library.dao.model.Genre;
import ru.otus.erinary.hw10.library.service.LibraryService;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

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

        mvc.perform(get("/library/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                .andExpect(jsonPath("$[*].year").isNotEmpty())
                .andExpect(jsonPath("$[*].author").isNotEmpty())
                .andExpect(jsonPath("$[*].genre").isNotEmpty());
    }

    @Test
    void getBook() throws Exception {
        var firstBook = createBook("title1", 1970, "author", "genre");
        Mockito.when(libraryService.getBookById(firstBook.getId()))
                .thenReturn(firstBook);

        mvc.perform(get("/library/book/{id}", firstBook.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(firstBook.getId()))
                .andExpect(jsonPath("$.title").value(firstBook.getTitle()))
                .andExpect(jsonPath("$.year").value(firstBook.getYear()))
                .andExpect(jsonPath("$.author").value(firstBook.getAuthor().getName()))
                .andExpect(jsonPath("$.genre").value(firstBook.getGenre().getName()));

        var secondBook = createBook("title2", 1980, null, null);
        Mockito.when(libraryService.getBookById(secondBook.getId()))
                .thenReturn(secondBook);

        mvc.perform(get("/library/book/{id}", secondBook.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(secondBook.getId()))
                .andExpect(jsonPath("$.title").value(secondBook.getTitle()))
                .andExpect(jsonPath("$.year").value(secondBook.getYear()))
                .andExpect(jsonPath("$.author").value("NOT SPECIFIED"))
                .andExpect(jsonPath("$.genre").value("NOT SPECIFIED"));
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
        var bookModel = new BookDto(1L, "title", 1970, "author", "genre", null);

        mvc.perform(post("/library/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookModel)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookModel.getId()))
                .andExpect(jsonPath("$.title").value(bookModel.getTitle()))
                .andExpect(jsonPath("$.year").value(bookModel.getYear()))
                .andExpect(jsonPath("$.author").value(bookModel.getAuthorName()))
                .andExpect(jsonPath("$.genre").value(bookModel.getGenreName()));
    }

    @Test
    void editBook() throws Exception {
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
        Mockito.when(libraryService.getBookComments(Mockito.anyLong()))
                .thenReturn(List.of(new Comment("text", "user", new Book())));
        var bookModel = new BookDto(1L, "title", 1970, "author", "genre", null);

        mvc.perform(put("/library/book/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookModel)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookModel.getId()))
                .andExpect(jsonPath("$.title").value(bookModel.getTitle()))
                .andExpect(jsonPath("$.year").value(bookModel.getYear()))
                .andExpect(jsonPath("$.author").value(bookModel.getAuthorName()))
                .andExpect(jsonPath("$.genre").value(bookModel.getGenreName()))
                .andExpect(jsonPath("$.comments").isNotEmpty());
    }

    @Test
    void deleteBook() throws Exception {
        mvc.perform(delete("/library/book/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

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