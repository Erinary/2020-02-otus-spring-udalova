package ru.otus.erinary.hw11.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.erinary.hw11.library.api.model.AuthorDto;
import ru.otus.erinary.hw11.library.service.LibraryService;

@RestController
@RequestMapping("/library")
public class AuthorController {

    private final LibraryService libraryService;

    @Autowired
    public AuthorController(final LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/author")
    public Flux<AuthorDto> getAllAuthors() {
        return libraryService.getAuthorsFlux()
                .map(ModelConverter::toAuthorModel);
    }

    @GetMapping("/author/{id}")
    public Mono<AuthorDto> getAuthor(@PathVariable(value = "id") final String id) {
        return libraryService.getAuthorByIdMono(id)
                .map(ModelConverter::toAuthorModel);
    }

    @DeleteMapping("/author/{id}")
    public Mono<Void> deleteAuthor(@PathVariable(value = "id") final String id) {
        return libraryService.deleteAuthorMono(id);
    }
}
