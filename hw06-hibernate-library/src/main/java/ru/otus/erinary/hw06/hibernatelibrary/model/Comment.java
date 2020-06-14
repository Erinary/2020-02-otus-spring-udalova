package ru.otus.erinary.hw06.hibernatelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@SuppressWarnings("JpaDataSourceORMInspection")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "text", columnDefinition = "CLOB")
    private String text;

    @Column(name = "user")
    private String user;

    @Column(name = "date")
    private ZonedDateTime date;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Comment(final String text, final String user, final Book book) {
        this.text = text;
        this.user = user;
        this.date = ZonedDateTime.now();
        this.book = book;
    }
}
