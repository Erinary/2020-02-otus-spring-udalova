package ru.otus.erinary.hw07.springdatalibrary.service.shell;

import ru.otus.erinary.hw07.springdatalibrary.model.Author;
import ru.otus.erinary.hw07.springdatalibrary.model.Book;
import ru.otus.erinary.hw07.springdatalibrary.model.Comment;
import ru.otus.erinary.hw07.springdatalibrary.model.Genre;

import java.util.List;

/**
 * Interface used to display collections of different entities in string format.
 */
public interface DataRenderer {

    /**
     * Generates a table with short information about books.
     *
     * @param books list of {@link Book}
     * @return a table with short books info
     */
    String getShortBookTable(List<Book> books);

    /**
     * Generates a table with full information about books.
     *
     * @param books list of {@link Book}
     * @return a table with full books info
     */
    String getFullBookTable(List<Book> books);

    /**
     * Generates a table with information about authors.
     *
     * @param authors list of {@link Author}
     * @return a table with authors info
     */
    String getAuthorTable(List<Author> authors);

    /**
     * Generates a table with information about genres.
     *
     * @param genres list of {@link Genre}
     * @return a table with genres info
     */
    String getGenreTable(List<Genre> genres);

    /**
     * Generates a table containing comments.
     *
     * @param comments list of {@link Comment}
     * @return a table with comments
     */
    String getCommentTable(List<Comment> comments);
}
