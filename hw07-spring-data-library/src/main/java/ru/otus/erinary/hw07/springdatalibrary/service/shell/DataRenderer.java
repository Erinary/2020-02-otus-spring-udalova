package ru.otus.erinary.hw07.springdatalibrary.service.shell;

import ru.otus.erinary.hw07.springdatalibrary.model.Author;
import ru.otus.erinary.hw07.springdatalibrary.model.Book;
import ru.otus.erinary.hw07.springdatalibrary.model.Comment;
import ru.otus.erinary.hw07.springdatalibrary.model.Genre;

import java.util.List;

public interface DataRenderer {

    String getShortBookTable(List<Book> books);

    String getFullBookTable(List<Book> books);

    String getAuthorTable(List<Author> authors);

    String getGenreTable(List<Genre> genres);

    String getCommentTable(List<Comment> comments);
}
