--liquibase formatted sql

--changeset application:1 dbms:postgresql
--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:f SELECT EXISTS (SELECT FROM pg_tables WHERE schemaname = 'public' AND tablename = 'extruder');
CREATE TABLE IF NOT EXISTS extruder (
    id       INTEGER PRIMARY KEY,
    circumference   NUMERIC NOT NULL
);

--changeset application:2 dbms:postgresql
--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:0 select count(*) from information_schema.constraint_column_usage where table_name = 'extruder' and constraint_name='extruder_ref_device_id_fk';
ALTER TABLE extruder
    ADD CONSTRAINT extruder_inf_device_id_fk FOREIGN KEY (id) REFERENCES device (id);


