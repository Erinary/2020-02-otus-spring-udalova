package ru.otus.erinary.hw08.library.dao.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import ru.otus.erinary.hw08.library.dao.model.Author;
import ru.otus.erinary.hw08.library.dao.model.Book;
import ru.otus.erinary.hw08.library.dao.model.Comment;
import ru.otus.erinary.hw08.library.dao.model.Genre;
import ru.otus.erinary.hw08.library.dao.repository.AuthorRepository;
import ru.otus.erinary.hw08.library.dao.repository.BookRepository;
import ru.otus.erinary.hw08.library.dao.repository.GenreRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ChangeLog(order = "001")
public class MongockChangeLog {

    @ChangeSet(order = "001", id = "insertAuthorsCollection", author = "Erinary")
    public void insertAuthors(final MongockTemplate mongockTemplate) {
        var first = new Author(UUID.randomUUID().toString(), "Стив Макконнелл", Collections.emptyList());
        var second = new Author(UUID.randomUUID().toString(), "Анджей Сапковский", Collections.emptyList());
        var third = new Author(UUID.randomUUID().toString(), "Олдос Хаксли", Collections.emptyList());

        mongockTemplate.insert(List.of(first, second, third), Author.class);
    }

    @ChangeSet(order = "002", id = "insertGenresCollection", author = "Erinary")
    public void insertGenres(final MongockTemplate mongockTemplate) {
        var first = new Genre(UUID.randomUUID().toString(), "Программирование", Collections.emptyList());
        var second = new Genre(UUID.randomUUID().toString(), "Фэнтези", Collections.emptyList());
        var third = new Genre(UUID.randomUUID().toString(), "Классическая зарубежная проза", Collections.emptyList());

        mongockTemplate.insert(List.of(first, second, third), Genre.class);
    }

    @ChangeSet(order = "003", id = "insertBooksCollection", author = "Erinary")
    public void insertBooks(final MongockTemplate mongockTemplate,
                            final AuthorRepository authorRepository,
                            final GenreRepository genreRepository) {
        var firstAuthor = authorRepository.findByName("Стив Макконнелл").orElseThrow();
        var firstGenre = genreRepository.findByName("Программирование").orElseThrow();
        var firstBook = new Book(
                UUID.randomUUID().toString(),
                "Совершенный код",
                2019,
                firstAuthor,
                firstGenre
        );
        firstAuthor.setBooks(List.of(firstBook));
        firstGenre.setBooks(List.of(firstBook));

        var secondAuthor = authorRepository.findByName("Анджей Сапковский").orElseThrow();
        var secondGenre = genreRepository.findByName("Фэнтези").orElseThrow();
        var secondBook = new Book(
                UUID.randomUUID().toString(),
                "Последнее желание",
                2018,
                secondAuthor,
                secondGenre
        );
        secondAuthor.setBooks(List.of(secondBook));
        secondGenre.setBooks(List.of(secondBook));

        var thirdAuthor = authorRepository.findByName("Олдос Хаксли").orElseThrow();
        var thirdGenre = genreRepository.findByName("Классическая зарубежная проза").orElseThrow();
        var thirdBook = new Book(
                UUID.randomUUID().toString(),
                "О дивный новый мир",
                2015,
                thirdAuthor,
                thirdGenre
        );
        thirdAuthor.setBooks(List.of(thirdBook));
        thirdGenre.setBooks(List.of(thirdBook));

        mongockTemplate.insert(List.of(firstBook, secondBook, thirdBook), Book.class);
        authorRepository.saveAll(List.of(firstAuthor, secondAuthor, thirdAuthor));
        genreRepository.saveAll(List.of(firstGenre, secondGenre, thirdGenre));
    }

    @ChangeSet(order = "004", id = "insertCommentsCollection", author = "Erinary")
    public void insertComments(final MongockTemplate mongockTemplate, final BookRepository bookRepository) {
        var firstBook = bookRepository.findByTitle("Совершенный код").orElseThrow();
        var firstComment = new Comment(
                "Книга содержит много отличных советов и рекомендаций, которые действительно помогают писать код лучше.",
                "Ваня",
                firstBook
        );

        var secondComment = new Comment(
                "Отличная книга для понимания многих важных вещей в мире программирования.",
                "Юрий",
                firstBook
        );

        var thirdComment = new Comment(
                "Отличная книга, можно сказать, классика. Всем рекомендую.",
                "Максим",
                firstBook
        );

        var secondBook = bookRepository.findByTitle("О дивный новый мир").orElseThrow();
        var fourthComment = new Comment(
                "Остроумная, абсурдная антиутопия служит замечательной иллюстрацией доведенного до крайности общества потребления.",
                "Фатима",
                secondBook
        );
        mongockTemplate.insert(List.of(firstComment, secondComment, thirdComment, fourthComment), Comment.class);
    }
}
