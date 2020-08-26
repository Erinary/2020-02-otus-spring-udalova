package ru.otus.erinary.hw13.library.dao.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    void findByUsername() {
        var user = repository.findByUsername("user1").orElseThrow();
        assertEquals("user1", user.getUsername());
        assertNotNull(user.getPassword());
    }
}