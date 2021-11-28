--liquibase formatted sql

--changeset application:drop-extruder-telemetry-report dbms:postgresql
drop table if exists extruder_telemetry_report;

--changeset application:drop-density-and-diameter-extruder-telemetry dbms:postgresql
alter table extruder_telemetry
    drop column if exists density,
    drop column if exists diameter;
