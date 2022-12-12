--liquibase formatted sql
--changeset <satoshi>:<create-movie-character-sequence-id>
CREATE SEQUENCE IF NOT EXISTS public.children
(
    id bigint NOT NULL,
    count double,
    rating bigint,
    complete_task bigint,
    incomlete_task bigint,
    CONSTRAINT movie_character_pk PRIMARY KEY (id)
);

--rollback DROP TABLE movie_character;
