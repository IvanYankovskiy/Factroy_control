package com.factory.control;

import com.factory.control.config.properties.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({AppProperties.class})
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControlApplication.class, args);
    }

}
