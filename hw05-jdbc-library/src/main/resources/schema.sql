CREATE TABLE genre(
    id INT AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE author(
    id INT AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE book (
    id INT AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    year INT,
    author_id INT,
    genre_ID INT,
    PRIMARY KEY (id),
    FOREIGN KEY (author_id) REFERENCES author(id),
    FOREIGN KEY (genre_ID) REFERENCES genre(id)
);
