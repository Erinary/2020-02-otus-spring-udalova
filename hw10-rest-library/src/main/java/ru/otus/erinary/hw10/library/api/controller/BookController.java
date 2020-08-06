package ru.otus.erinary.hw10.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.erinary.hw10.library.api.model.BookModel;
import ru.otus.erinary.hw10.library.service.LibraryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/library")
public class BookController {

    private final LibraryService libraryService;

    @Autowired
    public BookController(final LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/book")
    public List<BookModel> getAllBooks() {
        return libraryService.getBooks().stream()
                .map(ModelConverter::toShortBookModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/book/{id}")
    public BookModel getBook(@PathVariable(value = "id") final Long id) {
        var book = libraryService.getBookById(id);
        var comments = libraryService.getBookComments(id);
        return ModelConverter.toBookModel(book, comments);
    }

    @PostMapping("/book")
    public BookModel saveBook(@RequestBody final BookModel bookModel) {
        var book = libraryService.saveBook(ModelConverter.toBookEntity(bookModel));
        return ModelConverter.toShortBookModel(book);
    }

    @PutMapping("/book/{id}")
    public BookModel editBook(@PathVariable(value = "id") final Long id, @RequestBody final BookModel bookModel) {
        var book = libraryService.saveBook(ModelConverter.toBookEntity(bookModel));
        var comments = libraryService.getBookComments(id);
        return ModelConverter.toBookModel(book, comments);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable(value = "id") final Long id) {
        libraryService.deleteBook(id);
    }
}
