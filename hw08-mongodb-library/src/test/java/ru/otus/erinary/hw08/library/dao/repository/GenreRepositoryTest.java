package ru.otus.erinary.hw08.library.dao.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.erinary.hw08.library.dao.model.Genre;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository repository;

    @Test
    void testSaveNew() {
        var genres = repository.findAll();
        assertFalse(genres.isEmpty());
        assertEquals(3, genres.size());

        var genre = new Genre("genre4");
        var id = repository.save(genre).getId();

        genres = repository.findAll();
        assertEquals(id, genre.getId());
        assertEquals(4, genres.size());
        assertTrue(genres.contains(genre));
    }

    @Test
    void testSaveExisted() {
        var genre = repository.findById(1L).orElseThrow();
        assertEquals("genre1", genre.getName());

        var newName = "genre5";
        genre.setName(newName);
        repository.save(genre);

        var loadedGenre = repository.findById(1L).orElseThrow();
        assertEquals(newName, loadedGenre.getName());
    }

    @Test
    void testFindById() {
        var genre = repository.findById(1L).orElseThrow();
        assertEquals("genre1", genre.getName());
        assertFalse(genre.getBooks().isEmpty());
    }

    @Test
    void testFindByName() {
        var author = repository.findByName("genre1").orElseThrow();
        assertEquals(1L, author.getId());
        assertFalse(author.getBooks().isEmpty());
    }

    @Test
    void testFindIdByName() {
        var id = repository.findIdByName("genre1").orElseThrow();
        assertEquals(1L, id);
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
    void delete() {
        var genres = repository.findAll();
        assertFalse(genres.isEmpty());
        assertEquals(3, genres.size());

        repository.deleteById(1L);
        genres = repository.findAll();
        assertEquals(2, genres.size());
        var genreIds = genres.stream().map(Genre::getId).collect(Collectors.toList());
        assertFalse(genreIds.contains(1L));
    }
}