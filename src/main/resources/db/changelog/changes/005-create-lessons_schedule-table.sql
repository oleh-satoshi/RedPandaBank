CREATE TABLE lessons_schedule
(
    id                BIGSERIAL PRIMARY KEY,
    lesson_start_time TIME,
    day               VARCHAR(255),
    lesson_id         BIGINT,
    FOREIGN KEY (lesson_id) REFERENCES lessons (id)
);