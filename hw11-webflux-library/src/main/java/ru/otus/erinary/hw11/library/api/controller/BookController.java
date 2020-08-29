package ru.otus.erinary.hw11.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.erinary.hw11.library.api.model.BookDto;
import ru.otus.erinary.hw11.library.service.LibraryService;

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
    public Flux<BookDto> getAllBooks() {
        return libraryService.getBooksFlux()
                .map(ModelConverter::toShortBookModel);
    }

    @GetMapping("/book/{id}")
    public Mono<BookDto> getBook(@PathVariable(value = "id") final String id) {
        return Mono.zip(
                libraryService.getBookByIdMono(id),
                libraryService.getBookCommentsFlux(id).collect(Collectors.toList()),
                ModelConverter::toBookModel
        );
    }

    @PostMapping("/book")
    public Mono<BookDto> saveBook(@RequestBody final BookDto bookDto) {
        return libraryService.saveBookMono(ModelConverter.toBookEntity(bookDto))
                .map(ModelConverter::toShortBookModel);
    }

    @PutMapping("/book/{id}")
    public Mono<BookDto> editBook(@PathVariable(value = "id") final String id, @RequestBody final BookDto bookDto) {
        return Mono.zip(
                libraryService.saveBookMono(ModelConverter.toBookEntity(bookDto)),
                libraryService.getBookCommentsFlux(id).collect(Collectors.toList()),
                ModelConverter::toBookModel
        );
    }

    @DeleteMapping("/book/{id}")
    public Mono<Void> deleteBook(@PathVariable(value = "id") final String id) {
        return libraryService.deleteBookMono(id);
    }
}
