package ru.otus.erinary.hw07.springdatalibrary.service.mapper;

import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Component;
import ru.otus.erinary.hw07.springdatalibrary.api.model.AuthorModel;
import ru.otus.erinary.hw07.springdatalibrary.api.model.BookShortModel;
import ru.otus.erinary.hw07.springdatalibrary.entity.Author;

import java.util.List;

/**
 * Mapper for conversion {@link Author} domain entity to {@link AuthorModel} DTO.
 */
@Component
public class AuthorMapper implements Mapper<Author, AuthorModel> {


    @Override
    public AuthorModel map(final Author author) {
        return map(author, true);
    }

    /**
     * Convert a {@link Author} entity to a {@link AuthorModel} DTO without a collection of books.
     *
     * @param author {@link Author}
     * @return {@link AuthorModel}
     */
    public AuthorModel mapWithoutBooks(final Author author) {
        return map(author, false);
    }

    private AuthorModel map(final Author author, final boolean includeBooks) {
        List<BookShortModel> bookList = null;
        if (includeBooks) {
            bookList = author.getBooks().stream()
                    .map(book -> new BookShortModel(book.getId(), book.getTitle(), book.getYear()))
                    .toList();
        }
        return new AuthorModel(author.getId(), author.getName(), ListUtils.emptyIfNull(bookList));
    }
}
