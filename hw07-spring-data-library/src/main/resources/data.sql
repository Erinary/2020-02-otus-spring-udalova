insert into genres (name)
values ('Программирование'),
       ('Фэнтези'),
       ('Классическая зарубежная проза');

insert into authors (name)
values ('Стив Макконнелл'),
       ('Анджей Сапковский'),
       ('Олдос Хаксли');

insert into books (title, edition_year, author_id, genre_id)
values ('Совершенный код',
        2019,
        (select id from authors where name = 'Стив Макконнелл'),
        (select id from genres where name = 'Программирование')),
       ('Последнее желание',
        2018,
        (select id from authors where name = 'Анджей Сапковский'),
        (select id from genres where name = 'Фэнтези')),
       ('О дивный новый мир',
        2015,
        (select id from authors where name = 'Олдос Хаксли'),
        (select id from genres where name = 'Классическая зарубежная проза'));

insert into comments(text, username, date, book_id)
values ('Книга содержит много отличных советов и рекомендаций, которые действительно помогают писать код лучше.',
        'Ваня',
        '2019-06-16 10:15:30 Europe/Moscow',
        (select id from books where title = 'Совершенный код')),
       ('Отличная книга для понимания многих важных вещей в мире программирования.',
        'Юрий',
        '2019-12-07 18:25:32 Asia/Novosibirsk',
        (select id from books where title = 'Совершенный код')),
       ('Отличная книга, можно сказать, классика. Всем рекомендую.',
        'Максим',
        '2019-04-26 11:05:30 Europe/Moscow',
        (select id from books where title = 'Совершенный код')),
       ('Остроумная, абсурдная антиутопия служит замечательной иллюстрацией доведенного до крайности общества потребления.',
        'Фатима',
        '2019-04-15 14:13:03 Europe/Moscow',
        (select id from books where title = 'О дивный новый мир'));