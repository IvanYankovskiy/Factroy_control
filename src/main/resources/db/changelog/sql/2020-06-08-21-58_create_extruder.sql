--liquibase formatted sql

--changeset application:1 dbms:postgresql
--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:f SELECT EXISTS (SELECT FROM pg_tables WHERE schemaname = 'public' AND tablename = 'extruder');
CREATE TABLE IF NOT EXISTS extruder (
    id          integer PRIMARY KEY,
    device_id   integer NOT NULL,
    counter     integer NOT NULL,
    density     numeric NOT NULL,
    diameter    numeric NOT NULL,
    time        timestamptz NOT NULL
);

--changeset application:2 dbms:postgresql
--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:0 select count(*) from information_schema.sequences where sequence_name='extruder_id_sequence' and sequence_schema='public';
CREATE SEQUENCE extruder_id_sequence;