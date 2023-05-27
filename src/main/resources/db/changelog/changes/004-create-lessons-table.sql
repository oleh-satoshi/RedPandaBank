CREATE TABLE lessons
(
    id         BIGSERIAL PRIMARY KEY,
    title      VARCHAR(255),
    teacher    VARCHAR(255),
    duration   INTEGER,
    child_id   BIGINT,
    is_deleted BOOLEAN
);