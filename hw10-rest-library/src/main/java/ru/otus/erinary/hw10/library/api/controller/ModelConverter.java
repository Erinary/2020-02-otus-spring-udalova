package ru.otus.erinary.hw10.library.api.controller;

import ru.otus.erinary.hw10.library.api.model.AuthorModel;
import ru.otus.erinary.hw10.library.api.model.BookModel;
import ru.otus.erinary.hw10.library.api.model.CommentModel;
import ru.otus.erinary.hw10.library.api.model.GenreModel;
import ru.otus.erinary.hw10.library.model.Author;
import ru.otus.erinary.hw10.library.model.Book;
import ru.otus.erinary.hw10.library.model.Comment;
import ru.otus.erinary.hw10.library.model.Genre;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModelConverter {

    static Book toBookEntity(final BookModel model) {
        return new Book(
                model.getId(),
                model.getTitle(),
                model.getYear(),
                new Author(model.getAuthorName()),
                new Genre(model.getGenreName())
        );
    }

    static BookModel toBookModel(final Book book, final List<Comment> comments) {
        return new BookModel(
                book.getId(),
                book.getTitle(),
                book.getYear(),
                Optional.ofNullable(book.getAuthor()).map(Author::getName).orElse("NOT SPECIFIED"),
                Optional.ofNullable(book.getGenre()).map(Genre::getName).orElse("NOT SPECIFIED"),
                comments.stream()
                        .map(ModelConverter::toCommentModel)
                        .collect(Collectors.toList())
        );
    }

    static BookModel toShortBookModel(final Book book) {
        return new BookModel(
                book.getId(),
                book.getTitle(),
                book.getYear(),
                Optional.ofNullable(book.getAuthor()).map(Author::getName).orElse("NOT SPECIFIED"),
                Optional.ofNullable(book.getGenre()).map(Genre::getName).orElse("NOT SPECIFIED"),
                null
        );
    }

    static AuthorModel toAuthorModel(final Author author) {
        return new AuthorModel(
                author.getId(),
                author.getName(),
                author.getBooks().stream()
                        .map(ModelConverter::toShortBookModel)
                        .collect(Collectors.toList())
        );
    }

    static GenreModel toGenreModel(final Genre genre) {
        return new GenreModel(
                genre.getId(),
                genre.getName(),
                genre.getBooks().stream()
                        .map(ModelConverter::toShortBookModel)
                        .collect(Collectors.toList())
        );
    }

    static CommentModel toCommentModel(final Comment comment) {
        return new CommentModel(
                comment.getId(),
                comment.getText(),
                comment.getUser(),
                comment.getDate(),
                comment.getBook().getId()
        );
    }
}
