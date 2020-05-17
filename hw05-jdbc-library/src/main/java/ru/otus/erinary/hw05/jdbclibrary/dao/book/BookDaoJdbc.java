package ru.otus.erinary.hw05.jdbclibrary.dao.book;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.erinary.hw05.jdbclibrary.dao.author.AuthorDao;
import ru.otus.erinary.hw05.jdbclibrary.dao.genre.GenreDao;
import ru.otus.erinary.hw05.jdbclibrary.model.Author;
import ru.otus.erinary.hw05.jdbclibrary.model.Book;
import ru.otus.erinary.hw05.jdbclibrary.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Имплементация {@link BookDao}
 */
@SuppressWarnings({"SqlResolve"})
@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbcOperations;
    private final BookExtractor extractor;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public BookDaoJdbc(final NamedParameterJdbcOperations jdbcOperations, final AuthorDao authorDao, final GenreDao genreDao) {
        this.jdbcOperations = jdbcOperations;
        this.extractor = new BookExtractor();
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public Book save(final Book book) {
        if (book.getId() == 0) {
            book.setId(insert(book));
        } else {
            update(book);
        }
        return book;
    }

    @SuppressWarnings("ConstantConditions")
    private long insert(final Book book) {
        var params = new MapSqlParameterSource();
        var author = book.getAuthor();
        if (author != null) {
            params.addValue("author_id", getAuthorId(author));
        }
        var genre = book.getGenre();
        if (genre != null) {
            params.addValue("genre_id", getGenreId(genre));
        }
        params.addValue("title", book.getTitle());
        params.addValue("year", book.getYear());
        var keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into books (title, year, author_id, genre_id)" +
                " values (:title, :year, :author_id, :genre_id)", params, keyHolder);
        return (long) keyHolder.getKey();
    }

    private void update(final Book book) {
        var params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("title", book.getTitle());
        params.addValue("year", book.getYear());
        var author = book.getAuthor();
        if (author != null) {
            params.addValue("author_id", getAuthorId(author));
        }
        var genre = book.getGenre();
        if (genre != null) {
            params.addValue("genre_id", getGenreId(genre));
        }
        jdbcOperations.update("update books set title = :title, year = :year, author_id = :author_id," +
                " genre_id = :genre_id where id = :id", params);
    }

    private long getAuthorId(final Author author) {
        return authorDao.findIdByName(author.getName())
                .orElseGet(() -> authorDao.insert(author));
    }

    private long getGenreId(final Genre genre) {
        return genreDao.findIdByName(genre.getName())
                .orElseGet(() -> genreDao.insert(genre));
    }

    @Override
    public Optional<Book> findById(final long id) {
        var params = new HashMap<String, Object>();
        params.put("id", id);
        var books = jdbcOperations.query(
                "select b.id, b.title, b.year," +
                        " a.id as author_id, a.name as author_name," +
                        " g.id as genre_id, g.name as genre_name" +
                        " from books as b" +
                        " left join authors as a on b.author_id = a.id" +
                        " left join genres as g on b.genre_id = g.id" +
                        " where b.id = :id", params, extractor);
        return Optional.ofNullable(Objects.requireNonNull(books).get(id));
    }

    @Override
    public List<Book> findAll() {
        var books = jdbcOperations.query(
                "select b.id, b.title, b.year," +
                        " a.id as author_id, a.name as author_name," +
                        " g.id as genre_id, g.name as genre_name" +
                        " from books as b" +
                        " left join authors as a on b.author_id = a.id" +
                        " left join genres as g on b.genre_id = g.id", extractor);
        return new ArrayList<>(Objects.requireNonNull(books).values());
    }

    @Override
    public List<Book> findAllByAuthorId(final long authorId) {
        var params = new HashMap<String, Object>();
        params.put("author_id", authorId);
        var books =  jdbcOperations.query(
                "select b.id, b.title, b.year," +
                        " a.id as author_id, a.name as author_name," +
                        " g.id as genre_id, g.name as genre_name" +
                        " from books as b" +
                        " left join authors as a on b.author_id = a.id" +
                        " left join genres as g on b.genre_id = g.id" +
                        " where b.author_id = :author_id", params, extractor);
        return new ArrayList<>(Objects.requireNonNull(books).values());
    }

    @Override
    public void delete(final long id) {
        var params = new HashMap<String, Object>();
        params.put("id", id);
        jdbcOperations.update("delete from books where id = :id", params);
    }

    private static class BookExtractor implements ResultSetExtractor<Map<Long, Book>> {

        @Override
        public Map<Long, Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
            var books = new HashMap<Long, Book>();
            var authors = new HashMap<Long, Author>();
            var genres = new HashMap<Long, Genre>();
            while (rs.next()) {
                var authorId = rs.getLong("author_id");
                var author = authors.get(authorId);
                if (author == null) {
                    author = new Author(authorId, rs.getString("author_name"), null);
                    authors.put(authorId, author);
                }

                var genreId = rs.getLong("author_id");
                var genre = genres.get(genreId);
                if (genre == null) {
                    genre = new Genre(genreId, rs.getString("genre_name"));
                    genres.put(genreId, genre);
                }

                var bookId = rs.getLong("id");
                var book = books.get(bookId);
                if (book == null) {
                    book = new Book(
                            bookId,
                            rs.getString("title"),
                            rs.getInt("year"),
                            author,
                            genre
                    );
                    books.put(bookId, book);
                }
            }
            return books;
        }
    }
}
