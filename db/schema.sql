CREATE TABLE post
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    description TEXT,
    created     date
);
CREATE TABLE candidate
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    description TEXT,
    created     date
);
