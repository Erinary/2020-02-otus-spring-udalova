insert into genres (name)
values ('genre1'),
       ('genre2'),
       ('genre3');

insert into authors (name)
values ('author1'),
       ('author2'),
       ('author3');

insert into books (title, year, author_id, genre_id)
values ('title1',
        2020,
        (select id from authors where name = 'author1'),
        (select id from genres where name = 'genre1')),
       ('title2',
        2019,
        (select id from authors where name = 'author2'),
        (select id from genres where name = 'genre2')),
       ('title3',
        2018,
        (select id from authors where name = 'author3'),
        (select id from genres where name = 'genre3')),
       ('title4',
        2018,
        (select id from authors where name = 'author1'),
        (select id from genres where name = 'genre2'));