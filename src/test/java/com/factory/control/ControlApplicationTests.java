package com.factory.control;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.liquibase.change-log=classpath:/testDb/testChangelog/testChangelogEntrypoint.xml",
        "spring.jpa.hibernate.ddl-auto=none"
})
class ControlApplicationTests {

    @Test
    void contextLoads() {
    }

}
