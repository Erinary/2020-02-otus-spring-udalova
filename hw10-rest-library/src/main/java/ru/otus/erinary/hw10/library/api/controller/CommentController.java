package ru.otus.erinary.hw10.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.erinary.hw10.library.api.model.CommentModel;
import ru.otus.erinary.hw10.library.service.LibraryService;

@RestController
@RequestMapping("/library")
public class CommentController {

    private final LibraryService libraryService;

    @Autowired
    public CommentController(final LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/comment")
    public CommentModel saveBookComment(@RequestBody final CommentModel commentModel) {
        var userName = commentModel.getUser() != null && !commentModel.getUser().isBlank() ? commentModel.getUser() : "Guest";
        var comment = libraryService.saveComment(
                commentModel.getText(),
                userName,
                commentModel.getBookId());
        return ModelConverter.toCommentModel(comment);
    }

    @DeleteMapping("/comment/{id}")
    public void deleteBookComment(@PathVariable(value = "id") final Long id) {
        libraryService.deleteComment(id);
    }
}
