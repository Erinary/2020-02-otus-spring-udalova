package ru.otus.erinary.hw06.hibernatelibrary.dao.genre;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.erinary.hw06.hibernatelibrary.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Имплементация {@link GenreRepository}
 */
@SuppressWarnings({"SqlResolve"})
@Repository
public class GenreDaoJdbc implements GenreRepository {

    private final NamedParameterJdbcOperations jdbcOperations;
    private final GenreRowMapper mapper;

    public GenreDaoJdbc(final NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
        this.mapper = new GenreRowMapper();
    }

    @Override
    public Long insert(final Genre genre) {
        var params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());
        var keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into genres (name) values (:name)", params, keyHolder);
        var id = (Long) keyHolder.getKey();
        genre.setId(id);
        return id;
    }

    @Override
    public void update(final Genre genre) {
        var params = new HashMap<String, Object>();
        params.put("id", genre.getId());
        params.put("name", genre.getName());
        jdbcOperations.update("update genres set name = :name where id = :id", params);
    }

    @Override
    public Optional<Genre> findById(final Long id) {
        var params = new HashMap<String, Object>();
        params.put("id", id);
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject("select * from genres where id = :id", params, mapper));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Genre> findByName(final String name) {
        var params = new HashMap<String, Object>();
        params.put("name", name);
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject("select * from genres where name = :name", params, mapper));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Long> findIdByName(final String name) {
        var params = new HashMap<String, Object>();
        params.put("name", name);
        return Optional.ofNullable(
                jdbcOperations.query("select id from genres where name = :name", params,
                        rs -> rs.next() ? rs.getLong("id") : null)
        );
    }

    @Override
    public List<Genre> findAll() {
        return jdbcOperations.query("select * from genres", mapper);
    }

    @Override
    public void delete(final Long id) {
        var params = new HashMap<String, Object>();
        params.put("id", id);
        jdbcOperations.update("delete from genres where id = :id", params);
    }

    private static class GenreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getLong("id");
            var name = rs.getString("name");
            return new Genre(id, name, null);
        }
    }

}
