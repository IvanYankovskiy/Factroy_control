--liquibase formatted sql

--changeset application:create-extruder-telemetry-report split dbms:postgresql
CREATE SEQUENCE extruder_tm_report_id_seq;

CREATE TABLE IF NOT EXISTS extruder_telemetry_report (
    id          INTEGER PRIMARY KEY,
    device_id   INTEGER NOT NULL,
    length      NUMERIC NOT NULL,
    weight      NUMERIC NOT NULL,
    time        TIMESTAMPTZ NOT NULL
);

ALTER TABLE extruder_telemetry_report
    ADD CONSTRAINT extrdr_report_device_id_fk FOREIGN KEY (device_id) REFERENCES device (id);

CREATE INDEX extrdr_time_idx ON extruder_telemetry_report (time);
