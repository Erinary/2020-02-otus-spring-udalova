package ru.otus.erinary.hw11.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
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
    public Mono<CommentDto> saveBookComment(@RequestBody final CommentDto commentDto) {
        var userName = commentDto.getUser() != null && !commentDto.getUser().isBlank() ? commentDto.getUser() : "Guest";
        return libraryService.saveCommentMono(
                commentDto.getText(),
                userName,
                commentDto.getBookId()
        ).map(ModelConverter::toCommentModel);
    }

    @DeleteMapping("/comment/{id}")
    public Mono<Void> deleteBookComment(@PathVariable(value = "id") final String id) {
        return libraryService.deleteCommentMono(id);
    }
}
