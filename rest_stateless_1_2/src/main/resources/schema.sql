CREATE TABLE artists (
    id SERIAL PRIMARY KEY,
    name VARCHAR(63)
);

CREATE TABLE songs(
    id SERIAL PRIMARY KEY,
    artistName varchar(63),
    name varchar(63),
    auditions int
);