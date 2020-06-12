--liquibase formatted sql

--changeset application:1 dbms:postgresql
--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:f SELECT EXISTS (SELECT FROM pg_tables WHERE schemaname = 'public' AND tablename = 'extruder');
CREATE TABLE IF NOT EXISTS extruder (
    id          integer PRIMARY KEY,
    counter     integer NOT NULL,
    density     numeric NOT NULL,
    diameter    numeric NOT NULL,
    time        timestamptz NOT NULL
);