package ru.otus.erinary.hw08.library.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.erinary.hw08.library.entity.Comment;

import java.util.List;

/**
 * Repository interface for {@link Comment}.
 */
public interface CommentRepository extends MongoRepository<Comment, String> {

    /**
     * Searches all comments related to the given book.
     *
     * @param bookId book's id
     * @return list of related comments
     */
    List<Comment> findAllByBookId(String bookId);

}
