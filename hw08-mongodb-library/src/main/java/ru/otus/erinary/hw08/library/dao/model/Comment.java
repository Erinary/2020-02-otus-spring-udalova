package ru.otus.erinary.hw08.library.dao.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
//@Entity
//@Table(name = "comments")
@Document(collection = "comments")
public class Comment {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    @Id
    private Long id;

//    @Lob
//    @Column(name = "text", columnDefinition = "CLOB")
    private String text;

//    @Column(name = "user")
    private String user;

//    @Column(name = "date")
    private ZonedDateTime date;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "book_id")
    private Book book;

    public Comment(final String text, final String user, final Book book) {
        this.text = text;
        this.user = user;
        this.date = ZonedDateTime.now();
        this.book = book;
    }
}
