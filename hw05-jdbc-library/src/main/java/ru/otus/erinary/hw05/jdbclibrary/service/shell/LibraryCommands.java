package ru.otus.erinary.hw05.jdbclibrary.service.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.erinary.hw05.jdbclibrary.service.LibraryService;

import java.util.Collections;

/**
 * Component with Spring Shell commands.
 */
@ShellComponent
public class LibraryCommands {

    private static final String NOT_FOUND = "Can not find [%s] by passed [%s]";

    private final LibraryService libraryService;
    private final DataRenderer dataRenderer;

    /**
     * Creates a new {@link LibraryCommands} instance.
     *
     * @param libraryService {@link LibraryService}
     * @param dataRenderer   {@link DataRenderer}
     */
    @Autowired
    public LibraryCommands(final LibraryService libraryService, final DataRenderer dataRenderer) {
        this.libraryService = libraryService;
        this.dataRenderer = dataRenderer;
    }

    /**
     * Returns rendered table with all authors in library.
     *
     * @return table of authors
     */
    @ShellMethod(key = "all-authors", value = "Get all authors in library")
    public String getAllAuthors() {
        var authors = libraryService.getAuthors();
        return dataRenderer.getAuthorTable(authors);
    }

    /**
     * Returns the author and the corresponding books, if any.
     *
     * @param name author's name
     * @return information about the author and their books
     */
    @ShellMethod(key = "author", value = "Get author by name")
    public String getAuthor(@ShellOption({"-n", "-name"}) final String name) {
        var author = libraryService.getAuthorByName(name);
        if (author == null) {
            return String.format(NOT_FOUND, "author", "name");
        } else {
            System.out.printf("ID: %d%n", author.getId());
            System.out.printf("NAME: %s%n", author.getName());
            return dataRenderer.getShortBookTable(author.getBooks());
        }
    }

    /**
     * Deletes the author.
     *
     * @param id author's id
     */
    @ShellMethod(key = "delete-author", value = "Delete author by id")
    public void deleteAuthor(@ShellOption({"-id"}) final Long id) {
        libraryService.deleteAuthor(id);
        System.out.printf("Author with id [%d] was deleted%n", id);
    }

    /**
     * Returns rendered table with all genres in library.
     *
     * @return table of genres
     */
    @ShellMethod(key = "all-genres", value = "Get all genres in library")
    public String getAllGenres() {
        var genres = libraryService.getGenres();
        return dataRenderer.getGenreTable(genres);
    }

    /**
     * Returns the genre and the corresponding books, if any.
     *
     * @param name genre's name
     * @return information about the genre and related books
     */
    @ShellMethod(key = "genre", value = "Get genre by name")
    public String getGenre(@ShellOption({"-n", "-name"}) final String name) {
        var genre = libraryService.getGenreByName(name);
        if (genre == null) {
            return String.format(NOT_FOUND, "genre", "name");
        } else {
            System.out.printf("ID: %d%n", genre.getId());
            System.out.printf("NAME: %s%n", genre.getName());
            return dataRenderer.getShortBookTable(genre.getBooks());
        }
    }

    /**
     * Deletes the genre.
     *
     * @param id genre's id
     */
    @ShellMethod(key = "delete-genre", value = "Delete genre by id")
    public void deleteGenre(@ShellOption({"-id"}) final Long id) {
        libraryService.deleteGenre(id);
        System.out.printf("Genre with id [%d] was deleted%n", id);
    }

    /**
     * Returns rendered table with all books in library.
     *
     * @return table of books
     */
    @ShellMethod(key = "all-books", value = "Get all books")
    public String getAllBooks() {
        var books = libraryService.getBooks();
        return dataRenderer.getFullBookTable(books);
    }

    /**
     * Returns table containing information about the book.
     *
     * @param id book's id
     * @return information about the book
     */
    @ShellMethod(key = "book", value = "Get book by id")
    public String getBook(@ShellOption({"-id"}) final Long id) {
        var book = libraryService.getBookById(id);
        if (book == null) {
            return String.format(NOT_FOUND, "book", "id");
        } else {
            return dataRenderer.getFullBookTable(Collections.singletonList(book));
        }
    }

    /**
     * Creates a new book or updates information about an existing one.
     *
     * @param id         book's id
     * @param title      book's title
     * @param year       edition year
     * @param authorName author's name
     * @param genreName  genre
     * @return information about created or updated book
     */
    @ShellMethod(key = "save-book", value = "Create new book or update existing")
    public String saveBook(
            @ShellOption(value = {"-id"}, defaultValue = ShellOption.NULL) final Long id,
            @ShellOption({"-t", "-title"}) final String title,
            @ShellOption({"-y", "-year"}) final int year,
            @ShellOption({"-a", "-author"}) final String authorName,
            @ShellOption({"-g", "-genre"}) final String genreName
    ) {
        var book = libraryService.saveBook(id, title, year, authorName, genreName);
        if (id != null) {
            System.out.println("Book was updated");
        } else {
            System.out.println("New book was saved");
        }
        return dataRenderer.getFullBookTable(Collections.singletonList(book));
    }

    /**
     * Deletes the book.
     *
     * @param id book's id
     */
    @ShellMethod(key = "delete-book", value = "Delete book by id")
    public void deleteBook(@ShellOption({"-id"}) final Long id) {
        libraryService.deleteBook(id);
        System.out.printf("Book with id [%d] was deleted%n", id);
    }

}
