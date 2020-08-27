package ru.otus.erinary.hw08.library.service.shell;

import ru.otus.erinary.hw08.library.dao.model.Author;
import ru.otus.erinary.hw08.library.dao.model.Book;
import ru.otus.erinary.hw08.library.dao.model.Comment;
import ru.otus.erinary.hw08.library.dao.model.Genre;

import java.util.List;

public interface DataRenderer {

    String getShortBookTable(List<Book> books);

    String getFullBookTable(List<Book> books);

    String getAuthorTable(List<Author> authors);

    String getGenreTable(List<Genre> genres);

    String getCommentTable(List<Comment> comments);
}
