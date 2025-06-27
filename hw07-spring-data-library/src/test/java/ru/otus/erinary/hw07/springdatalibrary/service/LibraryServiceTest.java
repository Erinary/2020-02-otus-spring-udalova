package ru.otus.erinary.hw07.springdatalibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.erinary.hw07.springdatalibrary.api.model.BookModel;
import ru.otus.erinary.hw07.springdatalibrary.api.model.CommentModel;
import ru.otus.erinary.hw07.springdatalibrary.dao.AuthorRepository;
import ru.otus.erinary.hw07.springdatalibrary.dao.BookRepository;
import ru.otus.erinary.hw07.springdatalibrary.dao.CommentRepository;
import ru.otus.erinary.hw07.springdatalibrary.dao.GenreRepository;
import ru.otus.erinary.hw07.springdatalibrary.entity.Author;
import ru.otus.erinary.hw07.springdatalibrary.entity.Book;
import ru.otus.erinary.hw07.springdatalibrary.entity.Comment;
import ru.otus.erinary.hw07.springdatalibrary.entity.Genre;
import ru.otus.erinary.hw07.springdatalibrary.service.mapper.AuthorMapper;
import ru.otus.erinary.hw07.springdatalibrary.service.mapper.BookMapper;
import ru.otus.erinary.hw07.springdatalibrary.service.mapper.CommentMapper;
import ru.otus.erinary.hw07.springdatalibrary.service.mapper.GenreMapper;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

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

    @BeforeEach
    void resetMocks() {
        Mockito.reset(authorRepository, genreRepository, bookRepository, commentRepository);
    }

    @Test
    public void testGetAuthorsSuccess() {
        var author = new Author("name");
        Mockito.when(authorRepository.findAll())
                .thenReturn(List.of(author));

        var modelList = libraryService.getAuthors();
        assertEquals(1, modelList.size());
        assertEquals(author.getName(), modelList.get(0).getName());
    }

    @Test
    public void testGetAuthorByNameSuccess() {
        var author = new Author("name");
        Mockito.when(authorRepository.findByName(author.getName()))
                .thenReturn(Optional.of(author));

        var model = libraryService.getAuthorByName(author.getName());
        assertEquals(author.getName(), model.getName());
    }

    @Test
    public void testDeleteAuthorSuccess() {
        libraryService.deleteAuthor(1L);
        Mockito.verify(authorRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void testGetGenresSuccess() {
        var genre = new Genre("genre");
        Mockito.when(genreRepository.findAll())
                .thenReturn(List.of(genre));

        var modelList = libraryService.getGenres();
        assertEquals(1, modelList.size());
        assertEquals(genre.getName(), modelList.get(0).getName());
    }

    @Test
    public void testGetGenreByNameSuccess() {
        var genre = new Genre("genre");
        Mockito.when(genreRepository.findByName(genre.getName()))
                .thenReturn(Optional.of(genre));

        var model = libraryService.getGenreByName(genre.getName());
        assertEquals(genre.getName(), model.getName());
    }

    @Test
    public void testDeleteGenreSuccess() {
        libraryService.deleteGenre(1L);
        Mockito.verify(genreRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void testGetBooksSuccess() {
        var book = new Book();
        book.setTitle("title");
        Mockito.when(bookRepository.findAll())
                .thenReturn(List.of(book));

        var modelList = libraryService.getBooks();
        assertEquals(1, modelList.size());
        assertEquals(book.getTitle(), modelList.get(0).getTitle());
    }

    @Test
    public void testGetBookByIdSuccess() {
        var book = new Book();
        book.setTitle("title");
        book.setYear(2020);
        book.setAuthor(new Author("author"));
        book.setGenre(new Genre("genre"));
        Mockito.when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));

        var model = libraryService.getBookById(1L);
        assertEquals(book.getTitle(), model.getTitle());
        assertEquals(book.getYear(), model.getYear());
        assertEquals(Optional.ofNullable(book.getAuthor()).map(Author::getName).orElse("empty"), model.getAuthor());
        assertEquals(Optional.ofNullable(book.getGenre()).map(Genre::getName).orElse("empty"), model.getGenre());
    }

    @Test
    public void testSaveNewBookSuccess() {
        var model = new BookModel(null, "title", 2020, "author", "genre");
        Mockito.when(bookRepository.save(Mockito.any(Book.class)))
                .thenAnswer(invocation -> {
                    Book b = invocation.getArgument(0);
                    var fieldId = b.getClass().getDeclaredField("id");
                    fieldId.setAccessible(true);
                    fieldId.set(b, 1L);
                    return b;
                });

        var saved = libraryService.saveBook(model);
        Mockito.verify(bookRepository, Mockito.times(1)).save(Mockito.any(Book.class));
        assertEquals(model.getTitle(), saved.getTitle());
    }

    @Test
    public void testDeleteBookSuccess() {
        libraryService.deleteBook(1L);
        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void testGetCommentsSuccess() {
        var id = 1L;
        var comment = new Comment("text", "user", ZonedDateTime.now(), new Book());
        Mockito.when(commentRepository.findAllByBookId(id))
                .thenReturn(List.of(comment));

        var modelList = libraryService.getBookComments(id);
        assertEquals(1, modelList.size());
        assertEquals(comment.getText(), modelList.get(0).getText());
        assertEquals(comment.getUsername(), modelList.get(0).getUsername());
    }

    @Test
    public void testSaveCommentSuccess() {
        var model = new CommentModel(null, 1L, "text", "user", ZonedDateTime.now());
        Mockito.when(bookRepository.findById(1L))
                .thenReturn(Optional.of(new Book()));
        Mockito.when(commentRepository.save(Mockito.any(Comment.class)))
                .thenAnswer(invocation -> {
                    Comment c = invocation.getArgument(0);
                    var fieldId = c.getClass().getDeclaredField("id");
                    fieldId.setAccessible(true);
                    fieldId.set(c, 1L);
                    return c;
                });

        var saved = libraryService.saveComment(model);
        Mockito.verify(bookRepository, Mockito.times(1)).findById(Mockito.any(Long.class));
        Mockito.verify(commentRepository, Mockito.times(1)).save(Mockito.any(Comment.class));
        assertNotNull(saved);
    }

    @Test
    public void testDeleteCommentSuccess() {
        libraryService.deleteComment(1L);
        Mockito.verify(commentRepository, Mockito.times(1)).deleteById(1L);
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

        @Bean
        public BookMapper bookMapper() {
            return new BookMapper();
        }

        @Bean
        public AuthorMapper authorMapper() {
            return new AuthorMapper();
        }

        @Bean
        public GenreMapper genreMapper() {
            return new GenreMapper();
        }

        @Bean
        public CommentMapper commentMapper() {
            return new CommentMapper();
        }
    }
}
