package ru.otus.erinary.hw10.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.erinary.hw10.library.api.model.CommentModel;
import ru.otus.erinary.hw10.library.service.LibraryService;

@Controller
@RequestMapping("/library")
public class CommentController {

    private final LibraryService libraryService;

    @Autowired
    public CommentController(final LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/comment/save")
    public String saveBookComment(@RequestParam(value = "id") final Long bookId, final Model model) {
        var commentModel = new CommentModel();
        commentModel.setBookId(bookId);
        model.addAttribute("commentModel", commentModel);
        return "comment-form";
    }

    @PostMapping("/comment/save")
    public String saveBookComment(final CommentModel commentModel) {
        var userName = commentModel.getUser() != null && !commentModel.getUser().isBlank() ? commentModel.getUser() : "Guest";
        var comment = libraryService.saveComment(
                commentModel.getText(),
                userName,
                commentModel.getBookId());
        return String.format("redirect:/library/book?id=%d", comment.getBook().getId());
    }

    @PostMapping("/comment/delete")
    public String deleteBookComment(@RequestParam(value = "id") final Long id) {
        var bookId = libraryService.getBookIdByComment(id);
        libraryService.deleteComment(id);
        return String.format("redirect:/library/book?id=%d", bookId);
    }
}
