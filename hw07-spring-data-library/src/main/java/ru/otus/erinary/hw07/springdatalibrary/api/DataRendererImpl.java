package ru.otus.erinary.hw07.springdatalibrary.api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.erinary.hw07.springdatalibrary.api.model.AuthorModel;
import ru.otus.erinary.hw07.springdatalibrary.api.model.BookModel;
import ru.otus.erinary.hw07.springdatalibrary.api.model.BookShortModel;
import ru.otus.erinary.hw07.springdatalibrary.api.model.CommentModel;
import ru.otus.erinary.hw07.springdatalibrary.api.model.GenreModel;

import java.time.format.DateTimeFormatter;
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
    private static final String[] COMMENT_COLUMNS = {"ID", "USER", "TEXT", "DATE"};

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String EMPTY_COMMENTS_MESSAGE = "[There are no comments yet]";
    private static final String EMPTY_CELL_VALUE = "<Not specified>";

    @Override
    public String getShortBookTable(final List<BookShortModel> books) {
        var bookData = applyBookShortModel(books);
        return generateTable(bookData);
    }

    @Override
    public String getFullBookTable(final List<BookModel> books) {
        var bookData = applyBookFullModel(books);
        return generateTable(bookData);
    }

    @Override
    public String getAuthorTable(final List<AuthorModel> authors) {
        var authorData = applyAuthorModel(authors);
        return generateTable(authorData);
    }

    @Override
    public String getGenreTable(final List<GenreModel> genres) {
        var genreData = applyGenreModel(genres);
        return generateTable(genreData);
    }

    @Override
    public String getCommentTable(final List<CommentModel> comments) {
        if (CollectionUtils.isEmpty(comments)) {
            return EMPTY_COMMENTS_MESSAGE;
        } else {
            var commentsData = applyCommentModel(comments);
            return generateTable(commentsData);
        }
    }

    private String generateTable(final Object[][] data) {
        var model = new ArrayTableModel(data);
        var builder = new TableBuilder(model);
        builder.addFullBorder(BorderStyle.oldschool);
        return builder.build().render(AVAILABLE_WIDTH);
    }

    @SuppressWarnings("DataFlowIssue")
    private String[][] applyBookShortModel(final List<BookShortModel> books) {
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

    @SuppressWarnings("DataFlowIssue")
    private String[][] applyBookFullModel(final List<BookModel> books) {
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
                        StringUtils.defaultIfBlank(book.getAuthor(), EMPTY_CELL_VALUE),
                        StringUtils.defaultIfBlank(book.getGenre(), EMPTY_CELL_VALUE)};
            }
            return bookData;
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private String[][] applyAuthorModel(final List<AuthorModel> authors) {
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
    private String[][] applyGenreModel(final List<GenreModel> genres) {
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

    private String[][] applyCommentModel(final List<CommentModel> comments) {
        var commentsData = new String[comments.size() + 1][];
        commentsData[0] = COMMENT_COLUMNS;
        for (int i = 1; i < commentsData.length; i++) {
            var comment = comments.get(i - 1);
            commentsData[i] = new String[]{
                    Long.toString(comment.getId()),
                    comment.getUsername(),
                    comment.getText(),
                    comment.getDate().format(FORMATTER)
            };
        }
        return commentsData;
    }

}
