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

insert into comments(text, user, date, book_id)
values ('comment text 1',
        'user1',
        '2019-06-16 10:15:30 Europe/Moscow',
        (select id from books where title = 'title1')),
       ('comment text 2',
        'user2',
        '2019-12-07 18:25:32 Asia/Novosibirsk',
        (select id from books where title = 'title1')),
       ('comment text 3',
        'user3',
        '2019-04-26 11:05:30 Europe/Moscow',
        (select id from books where title = 'title1')),
       ('comment text 4',
        'user4',
        '2019-04-15 14:13:03 Europe/Moscow',
        (select id from books where title = 'title2'));