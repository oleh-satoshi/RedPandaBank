CREATE TABLE IF NOT EXISTS translates (
    id bigint primary key,
    slug varchar(255) unique,
    value varchar(255),
    language varchar(255)
)
