package ru.otus.erinary.hw10.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.erinary.hw10.library.api.model.BookModel;
import ru.otus.erinary.hw10.library.service.LibraryService;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/library")
public class BookController {

    private final LibraryService libraryService;

    @Autowired
    public BookController(final LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    //TODO remove
    @GetMapping
    public String getHomePage() {
        return "home-page";
    }

    @GetMapping("/books")
    public String getAllBooks(final Model model) {
        var bookModels = libraryService.getBooks().stream()
                .map(b -> {
                    var comments = libraryService.getBookComments(b.getId());
                    return ModelConverter.toBookModel(b, comments);
                })
                .collect(Collectors.toList());
        model.addAttribute("bookModels", bookModels);
        return "books";
    }

    @GetMapping("/book")
    public String getBook(@RequestParam(value = "id") final Long id, final Model model) {
        var book = libraryService.getBookById(id);
        var comments = libraryService.getBookComments(id);
        model.addAttribute("bookModel", ModelConverter.toBookModel(book, comments));
        return "book-details";
    }

    @GetMapping("/book/save")
    public String saveBook(@RequestParam(value = "id", required = false) final Long id, final Model model) {
        BookModel bookModel;
        if (id != null) {
            var book = libraryService.getBookById(id);
            var comments = libraryService.getBookComments(id);
            bookModel = ModelConverter.toBookModel(book, comments);
        } else {
            bookModel = new BookModel();
        }
        model.addAttribute("bookModel", bookModel);
        return "book-form";
    }

    @PostMapping("/book/save")
    public String saveBook(final Model model, final BookModel bookModel) {
        var book = libraryService.saveBook(ModelConverter.toBookEntity(bookModel));
        if (bookModel.getId() == null) {
            return "redirect:/library/books";
        } else {
            var comments = libraryService.getBookComments(book.getId());
            model.addAttribute("bookModel", ModelConverter.toBookModel(book, comments));
            return "book-details";
        }
    }

    @PostMapping("/book/delete")
    public String deleteBook(@RequestParam(value = "id") final Long id) {
        libraryService.deleteBook(id);
        return "redirect:/library/books";
    }
}
