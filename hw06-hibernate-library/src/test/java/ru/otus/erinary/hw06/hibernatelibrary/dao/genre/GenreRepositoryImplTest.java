package ru.otus.erinary.hw06.hibernatelibrary.dao.genre;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.erinary.hw06.hibernatelibrary.model.Genre;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({GenreRepositoryImpl.class})
class GenreRepositoryImplTest {

    @Autowired
    private GenreRepositoryImpl repository;

    @Test
    void testInsert() {
        var genres = repository.findAll();
        assertFalse(genres.isEmpty());
        assertEquals(3, genres.size());

        var genre = new Genre("genre4");
        var id = repository.insert(genre);

        genres = repository.findAll();
        assertEquals(id, genre.getId());
        assertEquals(4, genres.size());
        assertTrue(genres.contains(genre));
    }

    @Test
    void testUpdate() {
        var genre = repository.findById(1L).orElseThrow();
        assertEquals("genre1", genre.getName());

        var newName = "genre5";
        genre.setName(newName);
        repository.update(genre);

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

        repository.delete(1L);
        genres = repository.findAll();
        assertEquals(2, genres.size());
        var genreIds = genres.stream().map(Genre::getId).collect(Collectors.toList());
        assertFalse(genreIds.contains(1L));
    }
}