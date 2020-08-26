package com.factory.control.dao_tests;

import com.factory.control.configuration.PostgresSharedContainer;
import com.factory.control.domain.entities.device.DeviceType;
import com.factory.control.domain.entities.device.Extruder;
import com.factory.control.repository.device.ExtruderRepository;
import org.junit.ClassRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@ContextConfiguration(initializers = PostgresSharedContainer.Initializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@DisplayName("ExtruderRepository DAO test")
class ExtruderRepositoryDAOTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = PostgresSharedContainer.getInstance();

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ExtruderRepository repository;

    @Test
    void test_saveExtruder() {
        Extruder extruder = new Extruder();
        extruder.setName("Extruder 1 full described");
        extruder.setDeviceType(DeviceType.EXTRUDER);
        extruder.setToken(UUID.randomUUID().toString());
        extruder.setCircumference(BigDecimal.valueOf(121.56));
        testEntityManager.persistAndFlush(extruder);

        //when
        Optional<Extruder> actual = repository.findById(extruder.getId());
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(extruder, actual.get());
    }
}
