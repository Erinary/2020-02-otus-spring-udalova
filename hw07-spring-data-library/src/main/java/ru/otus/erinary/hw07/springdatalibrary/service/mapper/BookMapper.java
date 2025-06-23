package ru.otus.erinary.hw07.springdatalibrary.service.mapper;

import org.springframework.stereotype.Component;
import ru.otus.erinary.hw07.springdatalibrary.api.model.BookModel;
import ru.otus.erinary.hw07.springdatalibrary.entity.Author;
import ru.otus.erinary.hw07.springdatalibrary.entity.Book;
import ru.otus.erinary.hw07.springdatalibrary.entity.Genre;

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
