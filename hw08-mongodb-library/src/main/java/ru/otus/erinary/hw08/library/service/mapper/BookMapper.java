package ru.otus.erinary.hw08.library.service.mapper;

import org.springframework.stereotype.Component;
import ru.otus.erinary.hw08.library.api.model.BookModel;
import ru.otus.erinary.hw08.library.entity.Author;
import ru.otus.erinary.hw08.library.entity.Book;
import ru.otus.erinary.hw08.library.entity.Genre;

import java.util.Optional;

/**
 * Mapper for conversion {@link Book} domain entity to {@link BookModel} DTO.
 */
@Component
public class BookMapper implements Mapper<Book, BookModel> {

    @Override
    public BookModel map(final Book book) {
        return new BookModel(book.getId(),
                book.getTitle(),
                book.getYear(),
                Optional.ofNullable(book.getAuthor()).map(Author::getName).orElse(null),
                Optional.ofNullable(book.getGenre()).map(Genre::getName).orElse(null));
    }
}
