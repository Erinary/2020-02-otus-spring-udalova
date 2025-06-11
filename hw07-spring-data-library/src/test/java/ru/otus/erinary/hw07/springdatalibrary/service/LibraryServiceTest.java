package ru.otus.erinary.hw07.springdatalibrary.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.erinary.hw07.springdatalibrary.dao.author.AuthorRepository;
import ru.otus.erinary.hw07.springdatalibrary.dao.book.BookRepository;
import ru.otus.erinary.hw07.springdatalibrary.dao.comment.CommentRepository;
import ru.otus.erinary.hw07.springdatalibrary.dao.genre.GenreRepository;
import ru.otus.erinary.hw07.springdatalibrary.model.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {LibraryServiceImpl.class, LibraryServiceTest.Configuration.class})
public class LibraryServiceTest {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LibraryService libraryService;

    @Test
    public void testGetAuthorsSuccess() {
        var author = new Author("name");
        Mockito.when(authorRepository.findAll())
                .thenReturn(List.of(author));

        var result = libraryService.getAuthors();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(author.getName(), result.get(0).getName());
    }

    @TestConfiguration
    static class Configuration {

        @Bean
        public BookRepository bookRepository() {
            return Mockito.mock(BookRepository.class);
        }

        @Bean
        public AuthorRepository authorRepository() {
            return Mockito.mock(AuthorRepository.class);
        }

        @Bean
        public GenreRepository genreRepository() {
            return Mockito.mock(GenreRepository.class);
        }

        @Bean
        public CommentRepository commentRepository() {
            return Mockito.mock(CommentRepository.class);
        }
    }
}
