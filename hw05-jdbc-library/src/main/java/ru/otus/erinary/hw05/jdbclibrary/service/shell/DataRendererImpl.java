package ru.otus.erinary.hw05.jdbclibrary.service.shell;

import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.erinary.hw05.jdbclibrary.model.Author;
import ru.otus.erinary.hw05.jdbclibrary.model.Book;
import ru.otus.erinary.hw05.jdbclibrary.model.Genre;

import java.util.List;

/**
 * Realization of {@link DataRenderer}.
 */
@Service
public class DataRendererImpl implements DataRenderer {

    private static final int AVAILABLE_WIDTH = 80;
    private static final String[] BOOK_COLUMNS = {"BOOK_ID", "TITLE", "YEAR"};
    private static final String[] FULL_BOOK_COLUMNS = {"ID", "TITLE", "YEAR", "AUTHOR", "GENRE"};
    private static final String[] AUTHOR_COLUMNS = {"ID", "NAME"};
    private static final String[] GENRE_COLUMNS = {"ID", "NAME"};

    @Override
    public String getShortBookTable(final List<Book> books) {
        var bookData = applyBookShortModel(books);
        return generateTable(bookData);
    }

    @Override
    public String getFullBookTable(final List<Book> books) {
        var bookData = applyBookFullModel(books);
        return generateTable(bookData);
    }

    @Override
    public String getAuthorTable(final List<Author> authors) {
        var authorData = applyAuthorModel(authors);
        return generateTable(authorData);
    }

    @Override
    public String getGenreTable(final List<Genre> genres) {
        var genreData = applyGenreModel(genres);
        return generateTable(genreData);
    }

    private String generateTable(final Object[][] data) {
        var model = new ArrayTableModel(data);
        var builder = new TableBuilder(model);
        builder.addFullBorder(BorderStyle.oldschool);
        return builder.build().render(AVAILABLE_WIDTH);
    }

    private String[][] applyBookShortModel(final List<Book> books) {
        if (CollectionUtils.isEmpty(books)) {
            return new String[][]{BOOK_COLUMNS};
        } else {
            var bookData = new String[books.size() + 1][];
            bookData[0] = BOOK_COLUMNS;
            for (int i = 1; i < bookData.length; i++) {
                var book = books.get(i - 1);
                bookData[i] = new String[]{
                        Long.toString(book.getId()),
                        book.getTitle(),
                        Integer.toString(book.getYear())};
            }
            return bookData;
        }
    }

    private String[][] applyBookFullModel(final List<Book> books) {
        if (CollectionUtils.isEmpty(books)) {
            return new String[][]{FULL_BOOK_COLUMNS};
        } else {
            var bookData = new String[books.size() + 1][];
            bookData[0] = FULL_BOOK_COLUMNS;
            for (int i = 1; i < bookData.length; i++) {
                var book = books.get(i - 1);
                bookData[i] = new String[]{
                        Long.toString(book.getId()),
                        book.getTitle(),
                        Integer.toString(book.getYear()),
                        book.getAuthor().getName(),
                        book.getGenre().getName()};
            }
            return bookData;
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private String[][] applyAuthorModel(final List<Author> authors) {
        if (CollectionUtils.isEmpty(authors)) {
            return new String[][]{AUTHOR_COLUMNS};
        } else {
            var authorData = new String[authors.size() + 1][];
            authorData[0] = AUTHOR_COLUMNS;
            for (int i = 1; i < authorData.length; i++) {
                var author = authors.get(i - 1);
                authorData[i] = new String[]{
                        Long.toString(author.getId()),
                        author.getName()
                };
            }
            return authorData;
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private String[][] applyGenreModel(final List<Genre> genres) {
        if (CollectionUtils.isEmpty(genres)) {
            return new String[][]{GENRE_COLUMNS};
        } else {
            var genreData = new String[genres.size() + 1][];
            genreData[0] = GENRE_COLUMNS;
            for (int i = 1; i < genreData.length; i++) {
                var genre = genres.get(i - 1);
                genreData[i] = new String[]{
                        Long.toString(genre.getId()),
                        genre.getName()
                };
            }
            return genreData;
        }
    }

}
