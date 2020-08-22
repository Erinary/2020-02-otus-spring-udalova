package ru.otus.erinary.hw08.library.dao.changelog.test;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import ru.otus.erinary.hw08.library.dao.model.Author;
import ru.otus.erinary.hw08.library.dao.model.Book;
import ru.otus.erinary.hw08.library.dao.model.Genre;
import ru.otus.erinary.hw08.library.dao.repository.AuthorRepository;
import ru.otus.erinary.hw08.library.dao.repository.GenreRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ChangeLog(order = "001")
public class MongockTestChangeLog {

    public static final String testAuthorId = UUID.randomUUID().toString();
    public static final String testGenreId = UUID.randomUUID().toString();
    public static final String testBookId = UUID.randomUUID().toString();

    @ChangeSet(order = "001", id = "insertAuthorsCollection", author = "Erinary")
    public void insertAuthors(final MongockTemplate mongockTemplate) {
        var first = new Author(testAuthorId, "author1", Collections.emptyList());
        var second = new Author(UUID.randomUUID().toString(), "author2", Collections.emptyList());
        var third = new Author(UUID.randomUUID().toString(), "author3", Collections.emptyList());

        mongockTemplate.insert(List.of(first, second, third), Author.class);
    }

    @ChangeSet(order = "002", id = "insertGenresCollection", author = "Erinary")
    public void insertGenres(final MongockTemplate mongockTemplate) {
        var first = new Genre(testGenreId, "genre1", Collections.emptyList());
        var second = new Genre(UUID.randomUUID().toString(), "genre2", Collections.emptyList());
        var third = new Genre(UUID.randomUUID().toString(), "genre3", Collections.emptyList());

        mongockTemplate.insert(List.of(first, second, third), Genre.class);
    }

    @ChangeSet(order = "003", id = "insertBooksCollection", author = "Erinary")
    public void insertBooks(final MongockTemplate mongockTemplate,
                            final AuthorRepository authorRepository,
                            final GenreRepository genreRepository) {
        var firstAuthor = authorRepository.findByName("author1").orElseThrow();
        var firstGenre = genreRepository.findByName("genre1").orElseThrow();
        var firstBook = new Book(
                testBookId,
                "title1",
                2020,
                firstAuthor,
                firstGenre
        );

        var secondAuthor = authorRepository.findByName("author2").orElseThrow();
        var secondGenre = genreRepository.findByName("genre2").orElseThrow();
        var secondBook = new Book(
                UUID.randomUUID().toString(),
                "title2",
                2019,
                secondAuthor,
                secondGenre
        );
        secondAuthor.setBooks(List.of(secondBook));
        secondGenre.setBooks(List.of(secondBook));

        var thirdAuthor = authorRepository.findByName("author3").orElseThrow();
        var thirdGenre = genreRepository.findByName("genre3").orElseThrow();
        var thirdBook = new Book(
                UUID.randomUUID().toString(),
                "title3",
                2018,
                thirdAuthor,
                thirdGenre
        );
        thirdAuthor.setBooks(List.of(thirdBook));
        thirdGenre.setBooks(List.of(thirdBook));

        var fourthBook = new Book(
                UUID.randomUUID().toString(),
                "title4",
                2018,
                firstAuthor,
                firstGenre
        );
        firstAuthor.setBooks(List.of(firstBook, fourthBook));
        firstGenre.setBooks(List.of(firstBook, fourthBook));

        mongockTemplate.insert(List.of(firstBook, secondBook, thirdBook, fourthBook), Book.class);
        authorRepository.saveAll(List.of(firstAuthor, secondAuthor, thirdAuthor));
        genreRepository.saveAll(List.of(firstGenre, secondGenre, thirdGenre));
    }
}
