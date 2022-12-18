package com.factory.control;

import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SpringBootTest(
        classes = {IntegrationTestConfig.class},
        properties= {
                "app.page-size=2"
        }
)
public @interface IntegrationTest {
}
