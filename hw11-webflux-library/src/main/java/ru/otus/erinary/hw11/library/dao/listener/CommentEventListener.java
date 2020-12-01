package ru.otus.erinary.hw11.library.dao.listener;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import ru.otus.erinary.hw11.library.dao.model.Comment;

public class CommentEventListener extends AbstractMongoEventListener<Comment> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Comment> event) {
        event.getSource().beforeConvert();
    }
}
