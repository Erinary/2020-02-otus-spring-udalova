package ru.otus.erinary.hw07.springdatalibrary.service.mapper;

import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Component;
import ru.otus.erinary.hw07.springdatalibrary.api.model.BookShortModel;
import ru.otus.erinary.hw07.springdatalibrary.api.model.GenreModel;
import ru.otus.erinary.hw07.springdatalibrary.entity.Genre;

import java.util.List;

/**
 * Mapper for conversion {@link Genre} domain entity to {@link GenreModel} DTO.
 */
@Component
public class GenreMapper implements Mapper<Genre, GenreModel> {

    @Override
    public GenreModel map(final Genre genre) {
        return map(genre, true);
    }

    /**
     * Convert a {@link Genre} entity to a {@link GenreModel} DTO without a collection of books.
     *
     * @param genre {@link Genre}
     * @return {@link GenreModel}
     */
    public GenreModel mapWithoutBooks(final Genre genre) {
        return map(genre, false);
    }

    private GenreModel map(final Genre genre, final boolean includeBooks) {
        List<BookShortModel> bookList = null;
        if (includeBooks) {
            bookList = genre.getBooks().stream()
                    .map(book -> new BookShortModel(book.getId(), book.getTitle(), book.getYear()))
                    .toList();
        }
        return new GenreModel(genre.getId(), genre.getName(), ListUtils.emptyIfNull(bookList));
    }
}
