package ru.otus.erinary.hw11.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.erinary.hw11.library.api.model.BookDto;
import ru.otus.erinary.hw11.library.service.LibraryService;

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
    public List<BookDto> getAllBooks() {
        return libraryService.getBooks().stream()
                .map(ModelConverter::toShortBookModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/book/{id}")
    public BookDto getBook(@PathVariable(value = "id") final String id) {
        var book = libraryService.getBookById(id);
        var comments = libraryService.getBookComments(id);
        return ModelConverter.toBookModel(book, comments);
    }

    @PostMapping("/book")
    public BookDto saveBook(@RequestBody final BookDto bookDto) {
        var book = libraryService.saveBook(ModelConverter.toBookEntity(bookDto));
        return ModelConverter.toShortBookModel(book);
    }

    @PutMapping("/book/{id}")
    public BookDto editBook(@PathVariable(value = "id") final String id, @RequestBody final BookDto bookDto) {
        var book = libraryService.saveBook(ModelConverter.toBookEntity(bookDto));
        var comments = libraryService.getBookComments(id);
        return ModelConverter.toBookModel(book, comments);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable(value = "id") final String id) {
        libraryService.deleteBook(id);
    }
}
