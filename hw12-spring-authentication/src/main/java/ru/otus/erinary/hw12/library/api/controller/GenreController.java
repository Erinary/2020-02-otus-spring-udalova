package ru.otus.erinary.hw12.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.erinary.hw12.library.service.LibraryService;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/library")
public class GenreController {

    private final LibraryService libraryService;

    @Autowired
    public GenreController(final LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/genres")
    public String getAllGenres(final Model model) {
        var genres = libraryService.getGenres().stream()
                .map(ModelConverter::toGenreDto)
                .collect(Collectors.toList());
        model.addAttribute("genres", genres);
        return "genres";
    }

    @GetMapping("/genre")
    public String getGenre(@RequestParam(value = "id") final Long id, final Model model) {
        var genre = libraryService.getGenreById(id);
        model.addAttribute("genre", ModelConverter.toGenreDto(genre));
        return "genre-details";
    }

    @PostMapping("/genre/delete")
    public String deleteGenre(@RequestParam(value = "id") final Long id) {
        libraryService.deleteGenre(id);
        return "redirect:/library/genres";
    }
}
