CREATE TABLE children
(
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT,
    count           NUMERIC,
    rating          INTEGER,
    complete_task   INTEGER,
    incomplete_task INTEGER,
    state           VARCHAR(255),
    is_skip         BOOLEAN,
    language        VARCHAR(255)
);
