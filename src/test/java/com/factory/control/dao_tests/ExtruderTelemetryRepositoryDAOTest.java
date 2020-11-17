package com.factory.control.dao_tests;

import com.factory.control.configuration.PostgresSharedContainer;
import com.factory.control.domain.entities.ExtruderTelemetry;
import com.factory.control.domain.entities.device.Device;
import com.factory.control.domain.entities.device.DeviceType;
import com.factory.control.repository.ExtruderTelemetryRepository;
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
import org.testcontainers.shaded.com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@ContextConfiguration(initializers = PostgresSharedContainer.Initializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@DisplayName("ExtruderTelemetryRepository DAO test")
class ExtruderTelemetryRepositoryDAOTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = PostgresSharedContainer.getInstance();

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ExtruderTelemetryRepository repository;

    @Test
    void test_selectByDeviceAndTimeBeforeAndTimeAfter() {
        Device device = createDevice();

        OffsetDateTime now = OffsetDateTime.now();
        ExtruderTelemetry entity1 = repository.save(new ExtruderTelemetry()
                .setDevice(device)
                .setCounter(80)
                .setDensity(BigDecimal.valueOf(1.0))
                .setDiameter(BigDecimal.valueOf(1.75))
                .setTime(now.minusMinutes(125))
        );
        ExtruderTelemetry entity2 = repository.save(new ExtruderTelemetry()
                .setDevice(device)
                .setCounter(45)
                .setDensity(BigDecimal.valueOf(1.0))
                .setDiameter(BigDecimal.valueOf(1.75))
                .setTime(now.minusMinutes(90))
        );
        ExtruderTelemetry entity3 = repository.save(new ExtruderTelemetry()
                .setDevice(device)
                .setCounter(45)
                .setDensity(BigDecimal.valueOf(1.0))
                .setDiameter(BigDecimal.valueOf(1.75))
                .setTime(now.minusMinutes(38))
        );
        repository.flush();

        //when
        Optional<List<ExtruderTelemetry>> actual = repository.findTelemetriesInPeriod(
                device.getId(), now.minusMinutes(120), now);

        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(2, actual.get().size());
        Assertions.assertEquals(Lists.newArrayList(entity2, entity3), actual.get());
    }

    private Device createDevice() {
        Device device = new Device()
                .setName("test device")
                .setToken(UUID.randomUUID().toString())
                .setDescription("description")
                .setDeviceType(DeviceType.EXTRUDER);
        device = testEntityManager.merge(device);
        testEntityManager.flush();
        return device;
    }

}
