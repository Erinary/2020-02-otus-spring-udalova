package ru.otus.erinary.hw05.jdbclibrary.service.shell;

import ru.otus.erinary.hw05.jdbclibrary.model.Author;
import ru.otus.erinary.hw05.jdbclibrary.model.Book;
import ru.otus.erinary.hw05.jdbclibrary.model.Genre;

import java.util.List;

public interface DataRenderer {

    String getShortBookTable(List<Book> books);

    String getFullBookTable(List<Book> books);

    String getAuthorTable(List<Author> authors);

    String getGenreTable(List<Genre> genres);
}
