package ru.otus.erinary.hw13.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.erinary.hw13.library.service.LibraryService;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/library")
public class AuthorController {

    private final LibraryService libraryService;

    @Autowired
    public AuthorController(final LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/authors")
    public String getAllAuthors(final Model model) {
        var authors = libraryService.getAuthors().stream()
                .map(ModelConverter::toAuthorDto)
                .collect(Collectors.toList());
        model.addAttribute("authors", authors);
        return "authors";
    }

    @GetMapping("/author")
    public String getAuthor(@RequestParam(value = "id") final Long id, final Model model) {
        var author = libraryService.getAuthorById(id);
        model.addAttribute("author", ModelConverter.toAuthorDto(author));
        return "author-details";
    }

    @PostMapping("/author/delete")
    public String deleteAuthor(@RequestParam(value = "id") final Long id) {
        libraryService.deleteAuthor(id);
        return "redirect:/library/authors";
    }

}
