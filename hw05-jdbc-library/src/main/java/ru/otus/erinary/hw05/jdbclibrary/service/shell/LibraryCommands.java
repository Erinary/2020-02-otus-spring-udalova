package ru.otus.erinary.hw05.jdbclibrary.service.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.erinary.hw05.jdbclibrary.service.LibraryService;

@ShellComponent
public class LibraryCommands {

    private static final String NOT_FOUND = "Can not find [%s] by passed [%s]";

    private final LibraryService libraryService;
    private final DataRenderer dataRenderer;

    @Autowired
    public LibraryCommands(final LibraryService libraryService, final DataRenderer dataRenderer) {
        this.libraryService = libraryService;
        this.dataRenderer = dataRenderer;
    }

    @ShellMethod(key = "all-authors", value = "Get all authors in library")
    public String getAllAuthors() {
        var authors = libraryService.getAuthors();
        return dataRenderer.getAuthorTable(authors);
    }

    @ShellMethod(key = "author", value = "Get author by name")
    public String getAuthor(@ShellOption({"-n", "-name"}) String name) {
        var author = libraryService.getAuthorByName(name);
        if (author == null) {
            return String.format(NOT_FOUND, "author", "name");
        } else {
            System.out.println(String.format("ID: %d", author.getId()));
            System.out.println(String.format("NAME: %s", author.getName()));
            return dataRenderer.getShortBookTable(author.getBooks());
        }
    }

    @ShellMethod(key = "all-genres", value = "Get all genres in library")
    public String getAllGenres() {
        var genres = libraryService.getGenres();
        return dataRenderer.getGenreTable(genres);
    }

    @ShellMethod(key = "genre", value = "Get genre by name")
    public String getGenre(@ShellOption({"-n", "-name"}) String name) {
        var genre = libraryService.getGenreByName(name);
        if (genre == null) {
            return String.format(NOT_FOUND, "genre", "name");
        } else {
            System.out.println(String.format("ID: %d", genre.getId()));
            System.out.println(String.format("NAME: %s", genre.getName()));
            return dataRenderer.getShortBookTable(genre.getBooks());
        }
    }

}
