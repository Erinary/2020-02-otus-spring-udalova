package ru.otus.erinary.hw12.library.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;
    private String title;
    private Integer year;
    private String authorName;
    private String genreName;
    private List<CommentDto> comments;

}
