package ru.otus.erinary.hw05.jdbclibrary.dao.genre;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.erinary.hw05.jdbclibrary.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Имплементация {@link GenreDao}
 */
@SuppressWarnings({"SqlResolve"})
@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbcOperations;
    private final GenreRowMapper mapper;
    private final GenreExtractor extractor;

    public GenreDaoJdbc(final NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
        this.mapper = new GenreRowMapper();
        this.extractor = new GenreExtractor();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public long insert(final Genre genre) {
        var params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());
        var keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into genres (name) values (:name)", params, keyHolder);
        var id = (long) keyHolder.getKey();
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
    public Optional<Genre> findById(final long id) {
        var params = new HashMap<String, Object>();
        params.put("id", id);
        return Optional.ofNullable(jdbcOperations.query("select * from genres where id = :id", params, extractor));
    }

    @Override
    public Optional<Genre> findByName(final String name) {
        var params = new HashMap<String, Object>();
        params.put("name", name);
        return Optional.ofNullable(jdbcOperations.query("select * from genres where name = :name", params, extractor));
    }

    @Override
    public Optional<Long> findIdByName(final String name) {
        var params = new HashMap<String, Object>();
        params.put("name", name);
        return Optional.ofNullable(
                jdbcOperations.query("select id from genres where name = :name", params,
                        rs -> rs.next() ? rs.getLong("id") : 0L)
        );
    }

    @Override
    public List<Genre> findAll() {
        return jdbcOperations.query("select * from genres", mapper);
    }

    @Override
    public void delete(final long id) {
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

    private static class GenreExtractor implements ResultSetExtractor<Genre> {

        @Override
        public Genre extractData(ResultSet rs) throws SQLException, DataAccessException {
            if (rs.next()) {
                return new Genre(
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
