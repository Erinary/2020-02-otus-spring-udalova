package ru.otus.erinary.hw13.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.erinary.hw13.library.api.model.CommentDto;
import ru.otus.erinary.hw13.library.service.LibraryService;

@Controller
@RequestMapping("/library")
public class CommentController {

    private final LibraryService libraryService;

    @Autowired
    public CommentController(final LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/comment/save")
    public String saveBookCommentView(@RequestParam(value = "id") final Long bookId, final Model model) {
        var comment = new CommentDto();
        comment.setBookId(bookId);
        model.addAttribute("comment", comment);
        return "comment-form";
    }

    @PostMapping("/comment/save")
    public String saveBookComment(final CommentDto commentDto) {
        var userDetails = (UserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        var userName = userDetails.getUsername();
        var comment = libraryService.saveComment(
                commentDto.getText(),
                userName,
                commentDto.getBookId());
        return String.format("redirect:/library/book?id=%d", comment.getBook().getId());
    }

    @PostMapping("/comment/delete")
    public String deleteBookComment(@RequestParam(value = "id") final Long id) {
        var bookId = libraryService.getBookIdByComment(id);
        libraryService.deleteComment(id);
        return String.format("redirect:/library/book?id=%d", bookId);
    }

}