package ru.otus.erinary.hw05.jdbclibrary.dao.author;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.erinary.hw05.jdbclibrary.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Имплементация {@link AuthorDao}
 */
@SuppressWarnings({"SqlResolve"})
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbcOperations;
    private final AuthorRowMapper mapper;
    private final AuthorExtractor extractor;

    public AuthorDaoJdbc(final NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
        this.mapper = new AuthorRowMapper();
        this.extractor = new AuthorExtractor();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public long insert(final Author author) {
        var params = new MapSqlParameterSource();
        params.addValue("name", author.getName());
        var keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into authors (name) values (:name)", params, keyHolder);
        var id = (long) keyHolder.getKey();
        author.setId(id);
        return id;
    }

    @Override
    public void update(final Author author) {
        var params = new HashMap<String, Object>();
        params.put("id", author.getId());
        params.put("name", author.getName());
        jdbcOperations.update("update authors set name = :name where id = :id", params);
    }

    @Override
    public Optional<Author> findById(final long id) {
        var params = new HashMap<String, Object>();
        params.put("id", id);
        return Optional.ofNullable(jdbcOperations.query("select * from authors where id = :id", params, extractor));
    }

    @Override
    public Optional<Author> findByName(final String name) {
        var params = new HashMap<String, Object>();
        params.put("name", name);
        return Optional.ofNullable(jdbcOperations.query("select * from authors where name = :name", params, extractor));
    }

    @Override
    public Optional<Long> findIdByName(final String name) {
        var params = new MapSqlParameterSource();
        params.addValue("name", name);
        return Optional.ofNullable(
                jdbcOperations.query("select id from authors where name = :name", params,
                        rs -> rs.next() ? rs.getLong("id") : 0L)
        );
    }

    @Override
    public List<Author> findAll() {
        return jdbcOperations.query("select * from authors", mapper);
    }

    @Override
    public void delete(final long id) {
        var params = new HashMap<String, Object>();
        params.put("id", id);
        jdbcOperations.update("delete from authors where id = :id", params);
    }

    private static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getLong("id");
            var name = rs.getString("name");
            return new Author(id, name, null);
        }
    }

    private static class AuthorExtractor implements ResultSetExtractor<Author> {

        @Override
        public Author extractData(ResultSet rs) throws SQLException, DataAccessException {
            if (rs.next()) {
                return new Author(
                        rs.getLong("id"),
                        rs.getString("name"),
                        null
                );
            } else {
                return null;
            }
        }
    }
}
