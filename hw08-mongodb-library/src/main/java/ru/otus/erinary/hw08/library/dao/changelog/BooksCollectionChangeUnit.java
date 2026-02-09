package ru.otus.erinary.hw08.library.dao.changelog;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.erinary.hw08.library.dao.repository.AuthorRepository;
import ru.otus.erinary.hw08.library.dao.repository.GenreRepository;
import ru.otus.erinary.hw08.library.entity.Author;
import ru.otus.erinary.hw08.library.entity.Book;
import ru.otus.erinary.hw08.library.entity.Genre;

import java.util.List;

@ChangeUnit(id = "create-book-collection", order = "003", author = "Erinary")
public class BooksCollectionChangeUnit {

    private final MongoTemplate mongoTemplate;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    /**
     * Creates a new {@link BooksCollectionChangeUnit} instance.
     *
     * @param mongoTemplate    {@link MongoTemplate}
     * @param authorRepository {@link AuthorRepository}
     * @param genreRepository  {@link GenreRepository}
     */
    public BooksCollectionChangeUnit(final MongoTemplate mongoTemplate,
                                     final AuthorRepository authorRepository,
                                     final GenreRepository genreRepository) {
        this.mongoTemplate = mongoTemplate;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    // CHECKSTYLE:OFF
    @Execution
    public void insertBooks() {
        var firstAuthor = authorRepository.findByName("Стив Макконнелл").orElseThrow();
        var firstGenre = genreRepository.findByName("Программирование").orElseThrow();
        var firstBook = new Book();
        firstBook.setTitle("Совершенный код");
        firstBook.setYear(2019);
        firstBook.setAuthor(firstAuthor);
        firstBook.setGenre(firstGenre);

        firstAuthor.addBook(firstBook);
        firstGenre.addBook(firstBook);

        var secondAuthor = authorRepository.findByName("Анджей Сапковский").orElseThrow();
        var secondGenre = genreRepository.findByName("Фэнтези").orElseThrow();
        var secondBook = new Book();
        secondBook.setTitle("Последнее желание");
        secondBook.setYear(2018);
        secondBook.setAuthor(secondAuthor);
        secondBook.setGenre(secondGenre);

        secondAuthor.addBook(secondBook);
        secondGenre.addBook(secondBook);

        var thirdAuthor = authorRepository.findByName("Олдос Хаксли").orElseThrow();
        var thirdGenre = genreRepository.findByName("Классическая зарубежная проза").orElseThrow();
        var thirdBook = new Book();
        thirdBook.setTitle("О дивный новый мир");
        thirdBook.setYear(2015);
        thirdBook.setAuthor(thirdAuthor);
        thirdBook.setGenre(thirdGenre);

        thirdAuthor.addBook(thirdBook);
        thirdGenre.addBook(thirdBook);

        mongoTemplate.insert(List.of(firstBook, secondBook, thirdBook), Book.class);
        authorRepository.saveAll(List.of(firstAuthor, secondAuthor, thirdAuthor));
        genreRepository.saveAll(List.of(firstGenre, secondGenre, thirdGenre));
    }
    // CHECKSTYLE:ON

    // CHECKSTYLE:OFF
    @RollbackExecution
    public void rollback() {
        var books = mongoTemplate.findAll(Book.class);

        for (Book book : books) {
            var bookId = book.getId();

            mongoTemplate.updateMulti(
                    new Query(Criteria.where("books._id").is(bookId)),
                    new Update().pull("books", Query.query(Criteria.where("_id").is(bookId))),
                    Author.class
            );

            mongoTemplate.updateMulti(
                    new Query(Criteria.where("books._id").is(bookId)),
                    new Update().pull("books", Query.query(Criteria.where("_id").is(bookId))),
                    Genre.class
            );
        }

        mongoTemplate.dropCollection(Book.class);
    }
    // CHECKSTYLE:ON
}
