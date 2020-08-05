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

//    @GetMapping("/comment/save")
//    public String saveBookComment(@RequestParam(value = "id") final Long bookId, final Model model) {
//        var commentModel = new CommentModel();
//        commentModel.setBookId(bookId);
//        model.addAttribute("commentModel", commentModel);
//        return "comment-form";
//    }

    @PostMapping("/comment")
    public CommentModel saveBookComment(final CommentModel commentModel) {
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
