package com.factory.control.configuration;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class TestDataSourceConfig {
    private static final String IMAGE_VERSION = "postgres:9.6";

    @Bean
    public PostgreSQLContainer container() {
        PostgreSQLContainer container = new PostgreSQLContainer(IMAGE_VERSION);
        container.start();
        return container;
    }

    @Primary
    @Bean
    public DataSource dataSource(PostgreSQLContainer container) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(container.getJdbcUrl());
        dataSource.setUser(container.getUsername());
        dataSource.setPassword(container.getPassword());
        log.info("Test DS was created");
        return dataSource;
    }
}
