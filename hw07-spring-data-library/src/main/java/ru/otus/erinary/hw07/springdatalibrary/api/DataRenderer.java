package ru.otus.erinary.hw07.springdatalibrary.api;

import ru.otus.erinary.hw07.springdatalibrary.api.model.AuthorModel;
import ru.otus.erinary.hw07.springdatalibrary.api.model.BookModel;
import ru.otus.erinary.hw07.springdatalibrary.api.model.BookShortModel;
import ru.otus.erinary.hw07.springdatalibrary.api.model.CommentModel;
import ru.otus.erinary.hw07.springdatalibrary.api.model.GenreModel;

import java.util.List;

/**
 * Interface used to display collections of different entities in string format.
 */
public interface DataRenderer {

    /**
     * Generates a table with short information about books.
     *
     * @param books list of {@link BookShortModel}
     * @return a table with short books info
     */
    String getShortBookTable(List<BookShortModel> books);

    /**
     * Generates a table with full information about books.
     *
     * @param books list of {@link BookModel}
     * @return a table with full books info
     */
    String getFullBookTable(List<BookModel> books);

    /**
     * Generates a table with information about authors.
     *
     * @param authors list of {@link AuthorModel}
     * @return a table with authors info
     */
    String getAuthorTable(List<AuthorModel> authors);

    /**
     * Generates a table with information about genres.
     *
     * @param genres list of {@link GenreModel}
     * @return a table with genres info
     */
    String getGenreTable(List<GenreModel> genres);

    /**
     * Generates a table containing comments.
     *
     * @param comments list of {@link CommentModel}
     * @return a table with comments
     */
    String getCommentTable(List<CommentModel> comments);
}
