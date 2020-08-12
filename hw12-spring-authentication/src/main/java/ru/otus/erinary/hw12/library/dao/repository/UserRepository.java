package ru.otus.erinary.hw12.library.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.erinary.hw12.library.dao.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
