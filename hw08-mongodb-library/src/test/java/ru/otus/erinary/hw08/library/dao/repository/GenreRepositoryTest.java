package ru.otus.erinary.hw08.library.dao.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.erinary.hw08.library.config.MongoConfig;
import ru.otus.erinary.hw08.library.dao.model.Genre;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.erinary.hw08.library.dao.changelog.test.MongockTestChangeLog.testGenreId;

@ExtendWith(SpringExtension.class)
@Import({MongoConfig.class})
@DataMongoTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository repository;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testSaveNew() {
        var genres = repository.findAll();
        assertFalse(genres.isEmpty());
        assertEquals(3, genres.size());

        var genre = new Genre("genre4");
        var id = repository.save(genre).getId();

        genres = repository.findAll();
        assertEquals(id, genre.getId());
        assertEquals(4, genres.size());
        var genresNames = genres.stream().map(Genre::getName).collect(Collectors.toList());
        assertTrue(genresNames.contains(genre.getName()));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testSaveExisted() {
        var genre = repository.findById(testGenreId).orElseThrow();
        assertEquals("genre1", genre.getName());

        var newName = "genre5";
        genre.setName(newName);
        repository.save(genre);

        var loadedGenre = repository.findById(testGenreId).orElseThrow();
        assertEquals(newName, loadedGenre.getName());
    }

    @Test
    void testFindById() {
        var genre = repository.findById(testGenreId).orElseThrow();
        assertEquals("genre1", genre.getName());
        assertFalse(genre.getBooks().isEmpty());
    }

    @Test
    void testFindByName() {
        var genre = repository.findByName("genre1").orElseThrow();
        assertEquals(testGenreId, genre.getId());
        assertFalse(genre.getBooks().isEmpty());
    }

    @Test
    void findAll() {
        var genres = repository.findAll();
        assertFalse(genres.isEmpty());
        assertEquals(3, genres.size());
        assertFalse(genres.get(0).getBooks().isEmpty());

        var genreNames = genres.stream().map(Genre::getName).collect(Collectors.toList());
        assertTrue(genreNames.containsAll(List.of("genre1", "genre2", "genre3")));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void delete() {
        var genres = repository.findAll();
        assertFalse(genres.isEmpty());
        assertEquals(3, genres.size());

        repository.deleteById(testGenreId);
        genres = repository.findAll();
        assertEquals(2, genres.size());
        var genreIds = genres.stream().map(Genre::getId).collect(Collectors.toList());
        assertFalse(genreIds.contains(testGenreId));
    }
}