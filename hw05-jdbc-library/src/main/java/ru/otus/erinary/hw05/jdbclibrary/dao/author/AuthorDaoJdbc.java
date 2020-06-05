package ru.otus.erinary.hw05.jdbclibrary.dao.author;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.erinary.hw05.jdbclibrary.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Имплементация {@link AuthorDao}
 */
@SuppressWarnings({"SqlResolve"})
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbcOperations;
    private final AuthorRowMapper mapper;

    public AuthorDaoJdbc(final NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
        this.mapper = new AuthorRowMapper();
    }

    @Override
    public Long insert(final Author author) {
        var params = new MapSqlParameterSource();
        params.addValue("name", author.getName());
        var keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into authors (name) values (:name)", params, keyHolder);
        var id = (Long) keyHolder.getKey();
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
    public Optional<Author> findById(final Long id) {
        var params = new HashMap<String, Object>();
        params.put("id", id);
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject("select * from authors where id = :id", params, mapper));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Author> findByName(final String name) {
        var params = new HashMap<String, Object>();
        params.put("name", name);
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject("select * from authors where name = :name", params, mapper));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Long> findIdByName(final String name) {
        var params = new MapSqlParameterSource();
        params.addValue("name", name);
        return Optional.ofNullable(
                jdbcOperations.query("select id from authors where name = :name", params,
                        rs -> rs.next() ? rs.getLong("id") : null)
        );
    }

    @Override
    public List<Author> findAll() {
        return jdbcOperations.query("select * from authors", mapper);
    }

    @Override
    public void delete(final Long id) {
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

}
