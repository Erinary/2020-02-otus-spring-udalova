package ru.otus.erinary.hw08.library.service.mapper;

import org.springframework.stereotype.Component;
import ru.otus.erinary.hw08.library.api.model.CommentModel;
import ru.otus.erinary.hw08.library.entity.Comment;

/**
 * Mapper for conversion {@link Comment} domain entity to {@link CommentModel} DTO.
 */
@Component
public class CommentMapper implements Mapper<Comment, CommentModel> {

    @Override
    public CommentModel map(final Comment comment) {
        return new CommentModel(comment.getId(),
                comment.getBook().getId(),
                comment.getText(),
                comment.getUsername(),
                comment.getDate());
    }
}
