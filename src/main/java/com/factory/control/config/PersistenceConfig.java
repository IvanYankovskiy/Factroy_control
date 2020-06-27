package com.factory.control.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.factory.control.repository")
public class PersistenceConfig {
}
