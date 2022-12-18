package com.factory.control;

import com.factory.control.config.properties.AppProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;


@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties({AppProperties.class})
@ComponentScan(
        basePackageClasses = {ControlApplication.class},
        excludeFilters = {@ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                value = {ControlApplication.class})
        }
)
public class IntegrationTestConfig {
}
