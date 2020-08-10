package ru.otus.erinary.hw10.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.erinary.hw10.library.api.model.GenreDto;
import ru.otus.erinary.hw10.library.service.LibraryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/library")
public class GenreController {

    private final LibraryService libraryService;

    @Autowired
    public GenreController(final LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/genre")
    public List<GenreDto> getAllGenres() {
        return libraryService.getGenres().stream()
                .map(ModelConverter::toGenreModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/genre/{id}")
    public GenreDto getGenre(@PathVariable(value = "id") final Long id) {
        var genre = libraryService.getGenreById(id);
        return ModelConverter.toGenreModel(genre);
    }

    @DeleteMapping("/genre/{id}")
    public void deleteGenre(@PathVariable(value = "id") final Long id) {
        libraryService.deleteGenre(id);
    }
}
