--liquibase formatted sql

--changeset application:1 dbms:postgresql
--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:f SELECT EXISTS (SELECT FROM pg_tables WHERE schemaname = 'public' AND tablename = 'extruder_telemetry');
CREATE TABLE IF NOT EXISTS extruder_telemetry (
    id          integer PRIMARY KEY,
    device_id   integer NOT NULL,
    counter     integer NOT NULL,
    density     numeric NOT NULL,
    diameter    numeric NOT NULL,
    time        timestamptz NOT NULL
);

--changeset application:2 dbms:postgresql
--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:0 select count(*) from information_schema.sequences where sequence_name='extruder_tm_id_sequence' and sequence_schema='public';
CREATE SEQUENCE extruder_tm_id_sequence;

--changeset application:3 dbms:postgresql
--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:0 select count(*) from information_schema.constraint_column_usage where table_name = 'extruder_telemetry' and constraint_name='extruder_device_id_fk';
ALTER TABLE extruder_telemetry
    ADD CONSTRAINT extruder_device_id_fk FOREIGN KEY (device_id) REFERENCES device (id);