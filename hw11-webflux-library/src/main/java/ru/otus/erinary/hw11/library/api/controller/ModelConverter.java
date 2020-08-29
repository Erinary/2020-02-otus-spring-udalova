package ru.otus.erinary.hw11.library.api.controller;

import ru.otus.erinary.hw11.library.api.model.AuthorDto;
import ru.otus.erinary.hw11.library.api.model.BookDto;
import ru.otus.erinary.hw11.library.api.model.CommentDto;
import ru.otus.erinary.hw11.library.api.model.GenreDto;
import ru.otus.erinary.hw11.library.dao.model.Author;
import ru.otus.erinary.hw11.library.dao.model.Book;
import ru.otus.erinary.hw11.library.dao.model.Comment;
import ru.otus.erinary.hw11.library.dao.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModelConverter {

    static Book toBookEntity(final BookDto model) {
        return new Book(
                model.getId(),
                model.getTitle(),
                model.getYear(),
                new Author(model.getAuthorName()),
                new Genre(model.getGenreName())
        );
    }

    static BookDto toBookModel(final Book book, final List<Comment> comments) {
        return new BookDto(
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

    static BookDto toShortBookModel(final Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getYear(),
                Optional.ofNullable(book.getAuthor()).map(Author::getName).orElse("NOT SPECIFIED"),
                Optional.ofNullable(book.getGenre()).map(Genre::getName).orElse("NOT SPECIFIED"),
                null
        );
    }

    static AuthorDto toAuthorModel(final Author author) {
        return new AuthorDto(
                author.getId(),
                author.getName(),
                (author.getBooks() == null) ? null : author.getBooks().stream()
                        .map(ModelConverter::toShortBookModel)
                        .collect(Collectors.toList())
        );
    }

    static GenreDto toGenreModel(final Genre genre) {
        return new GenreDto(
                genre.getId(),
                genre.getName(),
                genre.getBooks().stream()
                        .map(ModelConverter::toShortBookModel)
                        .collect(Collectors.toList())
        );
    }

    static CommentDto toCommentModel(final Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getUser(),
                comment.getDate(),
                comment.getBook().getId()
        );
    }
}
