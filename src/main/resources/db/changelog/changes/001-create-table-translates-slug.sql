CREATE TABLE IF NOT EXISTS translates
(
    language TEXT,
    slug     TEXT UNIQUE,
    value    TEXT
);