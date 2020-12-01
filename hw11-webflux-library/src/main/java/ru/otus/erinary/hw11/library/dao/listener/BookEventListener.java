package ru.otus.erinary.hw11.library.dao.listener;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import ru.otus.erinary.hw11.library.dao.model.Book;

public class BookEventListener extends AbstractMongoEventListener<Book> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        event.getSource().beforeConvert();
    }
}
