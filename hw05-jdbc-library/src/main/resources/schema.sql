CREATE TABLE genres
(
    id   BIGINT AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

CREATE TABLE authors
(
    id   BIGINT AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

CREATE TABLE books
(
    id           BIGINT AUTO_INCREMENT,
    title        VARCHAR(255) NOT NULL,
    edition_year INT,
    author_id    BIGINT,
    genre_id     BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (author_id) REFERENCES authors (id) ON DELETE SET NULL,
    FOREIGN KEY (genre_id) REFERENCES genres (id) ON DELETE SET NULL
);
