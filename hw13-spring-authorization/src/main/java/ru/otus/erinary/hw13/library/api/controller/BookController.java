package ru.otus.erinary.hw13.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.erinary.hw13.library.api.model.BookDto;
import ru.otus.erinary.hw13.library.service.LibraryService;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/library")
public class BookController {

    private final LibraryService libraryService;

    @Autowired
    public BookController(final LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/books")
    public String getAllBooks(final Model model) {
        var books = libraryService.getBooks().stream()
                .map(ModelConverter::toShortBookDto)
                .collect(Collectors.toList());
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/book")
    public String getBook(@RequestParam(value = "id") final Long id, final Model model) {
        var book = libraryService.getBookById(id);
        var comments = libraryService.getBookComments(id);
        model.addAttribute("book", ModelConverter.toBookDto(book, comments));
        return "book-details";
    }

    @GetMapping("/book/save")
    public String saveBookView(@RequestParam(value = "id", required = false) final Long id, final Model model) {
        BookDto bookDto;
        if (id != null) {
            var book = libraryService.getBookById(id);
            bookDto = ModelConverter.toShortBookDto(book);
        } else {
            bookDto = new BookDto();
        }
        model.addAttribute("book", bookDto);
        return "book-form";
    }

    @PostMapping("/book/save")
    public String saveBook(final Model model, final BookDto bookDto) {
        var book = libraryService.saveBook(ModelConverter.toBookEntity(bookDto));
        if (bookDto.getId() == null) {
            return "redirect:/library/books";
        } else {
            var comments = libraryService.getBookComments(book.getId());
            model.addAttribute("book", ModelConverter.toBookDto(book, comments));
            return "book-details";
        }
    }

    @PostMapping("/book/delete")
    public String deleteBook(@RequestParam(value = "id") final Long id) {
        libraryService.deleteBook(id);
        return "redirect:/library/books";
    }
}
