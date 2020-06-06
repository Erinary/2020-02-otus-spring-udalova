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