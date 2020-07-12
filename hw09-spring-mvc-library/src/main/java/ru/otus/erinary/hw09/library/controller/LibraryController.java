package ru.otus.erinary.hw09.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.erinary.hw09.library.service.LibraryService;

@Controller
@RequestMapping("/library")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(final LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public String getHomePage() {
        return "home-page";
    }

    @GetMapping("/authors")
    public String getAllAuthors(final Model model) {
        var authors = libraryService.getAuthors();
        model.addAttribute("authors", authors);
        return "authors";
    }

    @PostMapping("/author/delete")
    public String deleteAuthor(@RequestParam(value = "id") final Long id) {
        libraryService.deleteAuthor(id);
        return "redirect:/library/authors";
    }

    @GetMapping("/genres")
    public String getAllGenres(final Model model) {
        var genres = libraryService.getGenres();
        model.addAttribute("genres", genres);
        return "genres";
    }

    @PostMapping("/genre/delete")
    public String deleteGenre(@RequestParam(value = "id") final Long id) {
        libraryService.deleteGenre(id);
        return "redirect:/library/genres";
    }
}
