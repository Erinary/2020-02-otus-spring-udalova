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
import ru.otus.erinary.hw08.library.entity.Comment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({MongoConfig.class, MongockConfig.class})
@DataMongoTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CommentRepositoryTest extends MongoTestcontainersConfig {

    @Autowired
    private CommentRepository repository;

    @Test
    void testFindAll() {
        var comments = repository.findAll();
        assertFalse(comments.isEmpty());
        assertEquals(4, comments.size());
        assertNotNull(comments.get(0).getBook());

        var users = comments.stream().map(Comment::getUsername).toList();
        assertTrue(users.containsAll(List.of("user1", "user2", "user3", "user4")));
    }
}