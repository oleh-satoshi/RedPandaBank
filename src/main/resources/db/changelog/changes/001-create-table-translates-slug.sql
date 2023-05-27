CREATE TABLE IF NOT EXISTS translates
(
    id       BIGSERIAL PRIMARY KEY,
    language TEXT,
    slug     TEXT UNIQUE,
    value    TEXT
);