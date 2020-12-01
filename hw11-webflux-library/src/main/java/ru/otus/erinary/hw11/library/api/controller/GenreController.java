package ru.otus.erinary.hw11.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.erinary.hw11.library.api.model.GenreDto;
import ru.otus.erinary.hw11.library.service.LibraryService;

@RestController
@RequestMapping("/library")
public class GenreController {

    private final LibraryService libraryService;

    @Autowired
    public GenreController(final LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/genre")
    public Flux<GenreDto> getAllGenres() {
        return libraryService.getGenres()
                .map(ModelConverter::toGenreModel);
    }

    @GetMapping("/genre/{id}")
    public Mono<GenreDto> getGenre(@PathVariable(value = "id") final String id) {
        return libraryService.getGenreById(id)
                .map(ModelConverter::toGenreModel);
    }

    @DeleteMapping("/genre/{id}")
    public Mono<Void> deleteGenre(@PathVariable(value = "id") final String id) {
        return libraryService.deleteGenre(id);
    }
}
