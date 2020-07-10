package com.factory.control.configuration;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresSharedContainer extends PostgreSQLContainer<PostgresSharedContainer> {

    private static final String IMAGE_VERSION = "postgres:9.6";

    private static PostgresSharedContainer container;

    public PostgresSharedContainer() {
        super(IMAGE_VERSION);
    }

    public static PostgresSharedContainer getInstance() {
        if (container == null) {
            container = new PostgresSharedContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("FACTORY_DATABASE_URL", container.getJdbcUrl());
        System.setProperty("FACTORY_DATABASE_USERNAME", container.getUsername());
        System.setProperty("FACTORY_DATABASE_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
