package ru.otus.erinary.hw09.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.erinary.hw09.library.api.model.AuthorModel;
import ru.otus.erinary.hw09.library.api.model.BookModel;
import ru.otus.erinary.hw09.library.api.model.CommentModel;
import ru.otus.erinary.hw09.library.api.model.GenreModel;
import ru.otus.erinary.hw09.library.model.Comment;
import ru.otus.erinary.hw09.library.service.exception.LibraryServiceException;
import ru.otus.erinary.hw09.library.model.Author;
import ru.otus.erinary.hw09.library.model.Book;
import ru.otus.erinary.hw09.library.model.Genre;
import ru.otus.erinary.hw09.library.service.LibraryService;

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
                .map(this::toAuthorModel)
                .collect(Collectors.toList());
        model.addAttribute("authors", authors);
        return "authors";
    }

    @GetMapping("/author")
    public String getAuthor(@RequestParam(value = "id") final Long id, final Model model) {
        var author = libraryService.getAuthorById(id);
        model.addAttribute("author", toAuthorModel(author));
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
                .map(this::toGenreModel)
                .collect(Collectors.toList());
        model.addAttribute("genres", genres);
        return "genres";
    }

    @GetMapping("/genre")
    public String getGenre(@RequestParam(value = "id") final Long id, final Model model) {
        var genre = libraryService.getGenreById(id);
        model.addAttribute("genre", toGenreModel(genre));
        return "genre-details";
    }

    @PostMapping("/genre/delete")
    public String deleteGenre(@RequestParam(value = "id") final Long id) {
        libraryService.deleteGenre(id);
        return "redirect:/library/genres";
    }

    @GetMapping("/books")
    public String getAllBooks(final Model model) {
        var bookModels = libraryService.getBooks().stream()
                .map(this::toBookModel)
                .collect(Collectors.toList());
        model.addAttribute("bookModels", bookModels);
        return "books";
    }

    @GetMapping("/book")
    public String getBook(@RequestParam(value = "id") final Long id, final Model model) {
        var book = libraryService.getBookById(id);
        model.addAttribute("bookModel", toBookModel(book));
        return "book-details";
    }

    @GetMapping("/book/save")
    public String saveBook(@RequestParam(value = "id", required = false) final Long id, final Model model) {
        BookModel bookModel;
        if (id != null) {
            var book = libraryService.getBookById(id);
            bookModel = toBookModel(book);
        } else {
            bookModel = new BookModel();
        }
        model.addAttribute("bookModel", bookModel);
        return "book-form";
    }

    @PostMapping("/book/save")
    public String saveBook(final Model model, final BookModel bookModel) {
        var book = libraryService.saveBook(toBookEntity(bookModel));
        if (bookModel.getId() == null) {
            return "redirect:/library/books";
        } else {
            model.addAttribute("bookModel", toBookModel(book));
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
        var commentModel = new CommentModel();
        commentModel.setBookId(bookId);
        model.addAttribute("commentModel", commentModel);
        return "comment-form";
    }

    @PostMapping("/comment/save")
    public String saveBookComment(final CommentModel commentModel) {
        var userName = commentModel.getUser() != null && !commentModel.getUser().isBlank() ? commentModel.getUser() : "Guest";
        var comment = libraryService.saveComment(
                commentModel.getText(),
                userName,
                commentModel.getBookId());
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

    private Book toBookEntity(final BookModel model) {
        return new Book(
                model.getId(),
                model.getTitle(),
                model.getYear(),
                new Author(model.getAuthorName()),
                new Genre(model.getGenreName())
        );
    }

    private BookModel toBookModel(final Book book) {
        return new BookModel(
                book.getId(),
                book.getTitle(),
                book.getYear(),
                Optional.ofNullable(book.getAuthor()).map(Author::getName).orElse("NOT SPECIFIED"),
                Optional.ofNullable(book.getGenre()).map(Genre::getName).orElse("NOT SPECIFIED"),
                libraryService.getBookComments(book.getId()).stream()
                        .map(this::toCommentModel)
                        .collect(Collectors.toList())
        );
    }

    private AuthorModel toAuthorModel(final Author author) {
        return new AuthorModel(
                author.getId(),
                author.getName(),
                author.getBooks().stream()
                        .map(this::toBookModel)
                        .collect(Collectors.toList())
        );
    }

    private GenreModel toGenreModel(final Genre genre) {
        return new GenreModel(
                genre.getId(),
                genre.getName(),
                genre.getBooks().stream()
                        .map(this::toBookModel)
                        .collect(Collectors.toList())
        );
    }

    private CommentModel toCommentModel(final Comment comment) {
        return new CommentModel(
                comment.getId(),
                comment.getText(),
                comment.getUser(),
                comment.getDate().format(FORMATTER),
                comment.getBook().getId()
        );
    }
}
