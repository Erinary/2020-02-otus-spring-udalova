package ru.otus.erinary.hw10.library.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorModel {
    
    private Long id;
    private String name;
    private List<BookModel> books;
    
}
