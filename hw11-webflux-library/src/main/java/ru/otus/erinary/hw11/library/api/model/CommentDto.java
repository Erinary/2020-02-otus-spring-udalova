package ru.otus.erinary.hw11.library.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("text")
    private String text;

    @JsonProperty("user")
    private String user;

    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime date;

    @JsonProperty("bookId")
    private String bookId;
}
