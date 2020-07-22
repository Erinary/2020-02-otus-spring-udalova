package ru.otus.erinary.hw09.library.service.shell;

import ru.otus.erinary.hw09.library.service.exception.LibraryServiceException;
import ru.otus.erinary.hw09.library.service.LibraryService;

import java.util.Collections;

@SuppressWarnings("unused")
//@ShellComponent
public class LibraryCommands {

    private static final String NOT_FOUND = "Can not find [%s] by passed [%s]";

    private final LibraryService libraryService;
    private final DataRenderer dataRenderer;

    public LibraryCommands(final LibraryService libraryService, final DataRenderer dataRenderer) {
        this.libraryService = libraryService;
        this.dataRenderer = dataRenderer;
    }

//    @ShellMethod(key = "all-authors", value = "Get all authors in library")
    public String getAllAuthors() {
        var authors = libraryService.getAuthors();
        return dataRenderer.getAuthorTable(authors);
    }

//    @ShellMethod(key = "author", value = "Get author by name")
    public String getAuthor(/*@ShellOption({"-n", "-name"})*/ final String name) {
        var author = libraryService.getAuthorByName(name);
        if (author == null) {
            return String.format(NOT_FOUND, "author", "name");
        } else {
            System.out.println(String.format("ID: %d", author.getId()));
            System.out.println(String.format("NAME: %s", author.getName()));
            return dataRenderer.getShortBookTable(author.getBooks());
        }
    }

//    @ShellMethod(key = "delete-author", value = "Delete author by id")
    public void deleteAuthor(/*@ShellOption({"-id"})*/ final Long id) {
        libraryService.deleteAuthor(id);
        System.out.println(String.format("Author with id [%d] was deleted", id));
    }

//    @ShellMethod(key = "all-genres", value = "Get all genres in library")
    public String getAllGenres() {
        var genres = libraryService.getGenres();
        return dataRenderer.getGenreTable(genres);
    }

//    @ShellMethod(key = "genre", value = "Get genre by name")
    public String getGenre(/*@ShellOption({"-n", "-name"})*/ final String name) {
        var genre = libraryService.getGenreByName(name);
        if (genre == null) {
            return String.format(NOT_FOUND, "genre", "name");
        } else {
            System.out.println(String.format("ID: %d", genre.getId()));
            System.out.println(String.format("NAME: %s", genre.getName()));
            return dataRenderer.getShortBookTable(genre.getBooks());
        }
    }

//    @ShellMethod(key = "delete-genre", value = "Delete genre by id")
    public void deleteGenre(/*@ShellOption({"-id"})*/ final Long id) {
        libraryService.deleteGenre(id);
        System.out.println(String.format("Genre with id [%d] was deleted", id));
    }

//    @ShellMethod(key = "all-books", value = "Get all books")
    public String getAllBooks() {
        var books = libraryService.getBooks();
        return dataRenderer.getFullBookTable(books);
    }

//    @ShellMethod(key = "book", value = "Get book by id")
    public String getBook(
            /*@ShellOption({"-id"})*/ final Long id,
            /*@ShellOption(value = {"-c", "-comments"}, defaultValue = "false")*/ final boolean showComments) {
        var book = libraryService.getBookById(id);
        if (book == null) {
            return String.format(NOT_FOUND, "book", "id");
        } else {
            var bookInfo = dataRenderer.getFullBookTable(Collections.singletonList(book));
            if (!showComments) {
                return bookInfo;
            } else {
                var comments = libraryService.getBookComments(book.getId());
                System.out.println(bookInfo);
                System.out.println("Comments:");
                return dataRenderer.getCommentTable(comments);
            }
        }
    }

//    @ShellMethod(key = "save-book", value = "Create new book or update existing")
    public String saveBook(
            /*@ShellOption(value = {"-id"}, defaultValue = ShellOption.NULL)*/ final Long id,
            /*@ShellOption({"-t", "-title"})*/ final String title,
            /*@ShellOption({"-y", "-year"})*/ final int year,
            /*@ShellOption({"-a", "-author"})*/ final String authorName,
            /*@ShellOption({"-g", "-genre"})*/ final String genreName
    ) {
        try {
            var book = libraryService.saveBook(id, title, year, authorName, genreName);
            if (id != null) {
                System.out.println("Book was updated");
            } else {
                System.out.println("New book was saved");
            }
            return dataRenderer.getFullBookTable(Collections.singletonList(book));
        } catch (LibraryServiceException e) {
            return e.getMessage();
        }
    }

//    @ShellMethod(key = "delete-book", value = "Delete book by id")
    public void deleteBook(/*@ShellOption({"-id"})*/ final Long id) {
        libraryService.deleteBook(id);
        System.out.println(String.format("Book with id [%d] was deleted", id));
    }

//    @ShellMethod(key = "save-comment", value = "Save comment for book")
    public String addComment(
            /*@ShellOption({"-id"})*/ final Long id,
            /*@ShellOption({"-t", "-text"})*/ final String text,
            /*@ShellOption(value = {"-u", "-user"}, defaultValue = "Guest")*/ final String user) {
        try {
            var comment = libraryService.saveComment(text, user, id);
            return String.format("New comment with id [%d] was added", comment.getId());
        } catch (LibraryServiceException e) {
            return e.getMessage();
        }
    }

//    @ShellMethod(key = "delete-comment", value = "Delete book comment by id")
    public void deleteComment(/*@ShellOption({"-id"})*/ final Long id) {
        libraryService.deleteComment(id);
        System.out.println(String.format("Comment with id [%d] was deleted", id));
    }

}
