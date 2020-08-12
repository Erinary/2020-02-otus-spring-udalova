package ru.otus.erinary.hw12.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.erinary.hw12.library.api.model.AuthorDto;
import ru.otus.erinary.hw12.library.api.model.BookDto;
import ru.otus.erinary.hw12.library.api.model.CommentDto;
import ru.otus.erinary.hw12.library.api.model.GenreDto;
import ru.otus.erinary.hw12.library.dao.model.Author;
import ru.otus.erinary.hw12.library.dao.model.Book;
import ru.otus.erinary.hw12.library.dao.model.Comment;
import ru.otus.erinary.hw12.library.dao.model.Genre;
import ru.otus.erinary.hw12.library.service.LibraryService;
import ru.otus.erinary.hw12.library.service.exception.LibraryServiceException;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/library")
public class LibraryController {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(final LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public String getHomePage() {
        return "home-page";
    }

    @GetMapping("/authors")
    public String getAllAuthors(final Model model) {
        var authors = libraryService.getAuthors().stream()
                .map(this::toAuthorDto)
                .collect(Collectors.toList());
        model.addAttribute("authors", authors);
        return "authors";
    }

    @GetMapping("/author")
    public String getAuthor(@RequestParam(value = "id") final Long id, final Model model) {
        var author = libraryService.getAuthorById(id);
        model.addAttribute("author", toAuthorDto(author));
        return "author-details";
    }

    @PostMapping("/author/delete")
    public String deleteAuthor(@RequestParam(value = "id") final Long id) {
        libraryService.deleteAuthor(id);
        return "redirect:/library/authors";
    }

    @GetMapping("/genres")
    public String getAllGenres(final Model model) {
        var genres = libraryService.getGenres().stream()
                .map(this::toGenreDto)
                .collect(Collectors.toList());
        model.addAttribute("genres", genres);
        return "genres";
    }

    @GetMapping("/genre")
    public String getGenre(@RequestParam(value = "id") final Long id, final Model model) {
        var genre = libraryService.getGenreById(id);
        model.addAttribute("genre", toGenreDto(genre));
        return "genre-details";
    }

    @PostMapping("/genre/delete")
    public String deleteGenre(@RequestParam(value = "id") final Long id) {
        libraryService.deleteGenre(id);
        return "redirect:/library/genres";
    }

    @GetMapping("/books")
    public String getAllBooks(final Model model) {
        var books = libraryService.getBooks().stream()
                .map(this::toBookDto)
                .collect(Collectors.toList());
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/book")
    public String getBook(@RequestParam(value = "id") final Long id, final Model model) {
        var book = libraryService.getBookById(id);
        model.addAttribute("book", toBookDto(book));
        return "book-details";
    }

    @GetMapping("/book/save")
    public String saveBook(@RequestParam(value = "id", required = false) final Long id, final Model model) {
        BookDto bookDto;
        if (id != null) {
            var book = libraryService.getBookById(id);
            bookDto = toBookDto(book);
        } else {
            bookDto = new BookDto();
        }
        model.addAttribute("book", bookDto);
        return "book-form";
    }

    @PostMapping("/book/save")
    public String saveBook(final Model model, final BookDto bookDto) {
        var book = libraryService.saveBook(toBookEntity(bookDto));
        if (bookDto.getId() == null) {
            return "redirect:/library/books";
        } else {
            model.addAttribute("book", toBookDto(book));
            return "book-details";
        }
    }

    @PostMapping("/book/delete")
    public String deleteBook(@RequestParam(value = "id") final Long id) {
        libraryService.deleteBook(id);
        return "redirect:/library/books";
    }

    @GetMapping("/comment/save")
    public String saveBookComment(@RequestParam(value = "id") final Long bookId, final Model model) {
        var comment = new CommentDto();
        comment.setBookId(bookId);
        model.addAttribute("comment", comment);
        return "comment-form";
    }

    @PostMapping("/comment/save")
    public String saveBookComment(final CommentDto commentDto) {
        var userName = commentDto.getUser() != null && !commentDto.getUser().isBlank() ? commentDto.getUser() : "Guest";
        var comment = libraryService.saveComment(
                commentDto.getText(),
                userName,
                commentDto.getBookId());
        return String.format("redirect:/library/book?id=%d", comment.getBook().getId());
    }

    @PostMapping("/comment/delete")
    public String deleteBookComment(@RequestParam(value = "id") final Long id) {
        var bookId = libraryService.getBookIdByComment(id);
        libraryService.deleteComment(id);
        return String.format("redirect:/library/book?id=%d", bookId);
    }

    @GetMapping("/error")
    public void testErrorPage() {
        throw new LibraryServiceException("Some exception message");
    }

    private Book toBookEntity(final BookDto model) {
        return new Book(
                model.getId(),
                model.getTitle(),
                model.getYear(),
                new Author(model.getAuthorName()),
                new Genre(model.getGenreName())
        );
    }

    private BookDto toBookDto(final Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getYear(),
                Optional.ofNullable(book.getAuthor()).map(Author::getName).orElse("NOT SPECIFIED"),
                Optional.ofNullable(book.getGenre()).map(Genre::getName).orElse("NOT SPECIFIED"),
                libraryService.getBookComments(book.getId()).stream()
                        .map(this::toCommentDto)
                        .collect(Collectors.toList())
        );
    }

    private AuthorDto toAuthorDto(final Author author) {
        return new AuthorDto(
                author.getId(),
                author.getName(),
                author.getBooks().stream()
                        .map(this::toBookDto)
                        .collect(Collectors.toList())
        );
    }

    private GenreDto toGenreDto(final Genre genre) {
        return new GenreDto(
                genre.getId(),
                genre.getName(),
                genre.getBooks().stream()
                        .map(this::toBookDto)
                        .collect(Collectors.toList())
        );
    }

    private CommentDto toCommentDto(final Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getUser(),
                comment.getDate().format(FORMATTER),
                comment.getBook().getId()
        );
    }
}
