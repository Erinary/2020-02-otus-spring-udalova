package ru.otus.erinary.hw08.library.dao.changelog.test;

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

@ChangeUnit(id = "create-test-book-collection", order = "003", author = "Erinary")
public class TestBookChangeUnit {

    private final MongoTemplate mongoTemplate;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public TestBookChangeUnit(final MongoTemplate mongoTemplate,
                              final AuthorRepository authorRepository,
                              final GenreRepository genreRepository) {
        this.mongoTemplate = mongoTemplate;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Execution
    public void insertBooks() {
        var firstAuthor = authorRepository.findByName("author1").orElseThrow();
        var firstGenre = genreRepository.findByName("genre1").orElseThrow();
        var firstBook = new Book();
        firstBook.setTitle("title1");
        firstBook.setYear(2020);
        firstBook.setAuthor(firstAuthor);
        firstBook.setGenre(firstGenre);
        firstAuthor.addBook(firstBook);
        firstGenre.addBook(firstBook);

        var secondAuthor = authorRepository.findByName("author2").orElseThrow();
        var secondGenre = genreRepository.findByName("genre2").orElseThrow();
        var secondBook = new Book();
        secondBook.setTitle("title2");
        secondBook.setYear(2019);
        secondBook.setAuthor(secondAuthor);
        secondBook.setGenre(secondGenre);
        secondAuthor.addBook(secondBook);
        secondGenre.addBook(secondBook);

        var thirdAuthor = authorRepository.findByName("author3").orElseThrow();
        var thirdGenre = genreRepository.findByName("genre3").orElseThrow();
        var thirdBook = new Book();
        thirdBook.setTitle("title3");
        thirdBook.setYear(2018);
        thirdBook.setAuthor(thirdAuthor);
        thirdBook.setGenre(thirdGenre);
        thirdAuthor.addBook(thirdBook);
        thirdGenre.addBook(thirdBook);

        var fourthBook = new Book();
        fourthBook.setTitle("title4");
        fourthBook.setYear(2018);
        fourthBook.setAuthor(firstAuthor);
        fourthBook.setGenre(firstGenre);
        firstAuthor.addBook(fourthBook);
        firstGenre.addBook(fourthBook);

        mongoTemplate.insert(List.of(firstBook, secondBook, thirdBook, fourthBook), Book.class);
        authorRepository.saveAll(List.of(firstAuthor, secondAuthor, thirdAuthor));
        genreRepository.saveAll(List.of(firstGenre, secondGenre, thirdGenre));
    }

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
}
