package ru.otus.erinary.hw11.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.erinary.hw11.library.api.model.CommentDto;
import ru.otus.erinary.hw11.library.service.LibraryService;

@RestController
@RequestMapping("/library")
public class CommentController {

    private final LibraryService libraryService;

    @Autowired
    public CommentController(final LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/comment")
    public CommentDto saveBookComment(@RequestBody final CommentDto commentDto) {
        var userName = commentDto.getUser() != null && !commentDto.getUser().isBlank() ? commentDto.getUser() : "Guest";
        var comment = libraryService.saveComment(
                commentDto.getText(),
                userName,
                commentDto.getBookId());
        return ModelConverter.toCommentModel(comment);
    }

    @DeleteMapping("/comment/{id}")
    public void deleteBookComment(@PathVariable(value = "id") final String id) {
        libraryService.deleteComment(id);
    }
}
