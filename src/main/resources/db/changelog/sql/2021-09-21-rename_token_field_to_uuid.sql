--liquibase formatted sql

--changeset application:rename-token-to-uuid dbms:postgresql
alter table device
    rename column token to uuid;
