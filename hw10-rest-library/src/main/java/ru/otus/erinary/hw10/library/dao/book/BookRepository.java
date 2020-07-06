package ru.otus.erinary.hw10.library.dao.book;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.erinary.hw10.library.model.Book;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Book}
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findById(Long id);

    List<Book> findAllByAuthorId(Long authorId);

    List<Book> findAllByGenreId(Long genreId);

}
