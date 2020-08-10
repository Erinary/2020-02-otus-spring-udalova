package ru.otus.erinary.hw10.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.erinary.hw10.library.api.model.AuthorDto;
import ru.otus.erinary.hw10.library.service.LibraryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/library")
public class AuthorController {

    private final LibraryService libraryService;

    @Autowired
    public AuthorController(final LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/author")
    public List<AuthorDto> getAllAuthors() {
        return libraryService.getAuthors().stream()
                .map(ModelConverter::toAuthorModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/author/{id}")
    public AuthorDto getAuthor(@PathVariable(value = "id") final Long id) {
        var author = libraryService.getAuthorById(id);
        return ModelConverter.toAuthorModel(author);
    }

    @DeleteMapping("/author/{id}")
    public void deleteAuthor(@PathVariable(value = "id") final Long id) {
        libraryService.deleteAuthor(id);
    }
}
