insert into users(username, password)
values ('admin', '$2a$10$441wHYiAgIrvDOwJT.yob.6PThPNTcEPi54pDUqMie0D2s69upu26'),
       ('Perry', '$2a$10$arrB8.9C5UMBoKlltrTq8Oz7hL7aAla9rVfL8rwxkTB0WJ55w6YUC'),
       ('Nancy', '$2a$10$dpjGTg4JEuOpuq/owpFJtux2k4PYR7wx1Orx1DeLUy5mELSzIFfX6');

insert into genres (name)
values ('Программирование'),
       ('Фэнтези'),
       ('Классическая зарубежная проза');

insert into authors (name)
values ('Стив Макконнелл'),
       ('Анджей Сапковский'),
       ('Олдос Хаксли');

insert into books (title, year, author_id, genre_id)
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

insert into comments(text, user_id, date, book_id)
values ('Книга содержит много отличных советов и рекомендаций, которые действительно помогают писать код лучше.',
        (select id from users where username = 'Perry'),
        '2019-06-16 10:15:30 Europe/Moscow',
        (select id from books where title = 'Совершенный код')),
       ('Отличная книга для понимания многих важных вещей в мире программирования.',
        (select id from users where username = 'Nancy'),
        '2019-12-07 18:25:32 Asia/Novosibirsk',
        (select id from books where title = 'Совершенный код')),
       ('Отличная книга, можно сказать, классика. Всем рекомендую.',
        (select id from users where username = 'Perry'),
        '2019-04-26 11:05:30 Europe/Moscow',
        (select id from books where title = 'Совершенный код')),
       ('Остроумная, абсурдная антиутопия служит замечательной иллюстрацией доведенного до крайности общества потребления.',
        (select id from users where username = 'Nancy'),
        '2019-04-15 14:13:03 Europe/Moscow',
        (select id from books where title = 'О дивный новый мир'));