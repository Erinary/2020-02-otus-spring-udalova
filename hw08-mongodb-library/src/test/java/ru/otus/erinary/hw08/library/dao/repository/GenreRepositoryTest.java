package ru.otus.erinary.hw08.library.dao.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.erinary.hw08.library.config.MongoConfig;
import ru.otus.erinary.hw08.library.dao.config.MongoTestcontainersConfig;
import ru.otus.erinary.hw08.library.dao.config.MongockConfig;
import ru.otus.erinary.hw08.library.entity.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({MongoConfig.class, MongockConfig.class})
@DataMongoTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class GenreRepositoryTest extends MongoTestcontainersConfig {

    @Autowired
    private GenreRepository repository;

    @Test
    void testFindByName() {
        var genre = repository.findByName("genre1").orElseThrow();
        assertFalse(genre.getBooks().isEmpty());
    }

    @Test
    void findAll() {
        var genres = repository.findAll();
        assertFalse(genres.isEmpty());
        assertEquals(3, genres.size());
        assertFalse(genres.get(0).getBooks().isEmpty());

        var genreNames = genres.stream().map(Genre::getName).toList();
        assertTrue(genreNames.containsAll(List.of("genre1", "genre2", "genre3")));
    }

}