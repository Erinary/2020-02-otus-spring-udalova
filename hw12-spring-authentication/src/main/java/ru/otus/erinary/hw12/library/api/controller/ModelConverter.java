package ru.otus.erinary.hw12.library.api.controller;

import ru.otus.erinary.hw12.library.api.model.AuthorDto;
import ru.otus.erinary.hw12.library.api.model.BookDto;
import ru.otus.erinary.hw12.library.api.model.CommentDto;
import ru.otus.erinary.hw12.library.api.model.GenreDto;
import ru.otus.erinary.hw12.library.dao.model.Author;
import ru.otus.erinary.hw12.library.dao.model.Book;
import ru.otus.erinary.hw12.library.dao.model.Comment;
import ru.otus.erinary.hw12.library.dao.model.Genre;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModelConverter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static Book toBookEntity(final BookDto model) {
        return new Book(
                model.getId(),
                model.getTitle(),
                model.getYear(),
                new Author(model.getAuthorName()),
                new Genre(model.getGenreName())
        );
    }

    static BookDto toBookDto(final Book book, final List<Comment> comments) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getYear(),
                Optional.ofNullable(book.getAuthor()).map(Author::getName).orElse("NOT SPECIFIED"),
                Optional.ofNullable(book.getGenre()).map(Genre::getName).orElse("NOT SPECIFIED"),
                comments.stream()
                        .map(ModelConverter::toCommentDto)
                        .collect(Collectors.toList())
        );
    }

    static BookDto toShortBookDto(final Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getYear(),
                Optional.ofNullable(book.getAuthor()).map(Author::getName).orElse("NOT SPECIFIED"),
                Optional.ofNullable(book.getGenre()).map(Genre::getName).orElse("NOT SPECIFIED"),
                null
        );
    }

    static AuthorDto toAuthorDto(final Author author) {
        return new AuthorDto(
                author.getId(),
                author.getName(),
                author.getBooks().stream()
                        .map(ModelConverter::toShortBookDto)
                        .collect(Collectors.toList())
        );
    }

    static GenreDto toGenreDto(final Genre genre) {
        return new GenreDto(
                genre.getId(),
                genre.getName(),
                genre.getBooks().stream()
                        .map(ModelConverter::toShortBookDto)
                        .collect(Collectors.toList())
        );
    }

    static CommentDto toCommentDto(final Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getUser(),
                comment.getDate().format(FORMATTER),
                comment.getBook().getId()
        );
    }
}
