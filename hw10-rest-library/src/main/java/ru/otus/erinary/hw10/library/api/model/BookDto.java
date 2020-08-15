package ru.otus.erinary.hw10.library.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("author")
    private String authorName;

    @JsonProperty("genre")
    private String genreName;

    @JsonProperty("comments")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CommentDto> comments;

}
