package com.factory.control.repository;

import com.factory.control.configuration.OnlyDataJpaTest;
import com.factory.control.domain.entities.Device;
import com.factory.control.domain.entities.DeviceType;
import com.factory.control.domain.entities.ExtruderTelemetry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.testcontainers.shaded.com.google.common.collect.Lists;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@OnlyDataJpaTest
@DisplayName("ExtruderTelemetryRepository JPA only test")
class ExtruderTelemetryRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ExtruderTelemetryRepository repository;

    @AfterEach
    void clean() {
        repository.deleteAll();
    }

    @Test
    void testSelectByDeviceAndTimeBeforeAndTimeAfter() {
        // given
        Device device = createDevice();

        OffsetDateTime now = OffsetDateTime.now();
        repository.save(new ExtruderTelemetry()
                .setDevice(device)
                .setCounter(80)
                .setTime(now.minusMinutes(125))
        );
        ExtruderTelemetry entity2 = repository.save(new ExtruderTelemetry()
                .setDevice(device)
                .setCounter(45)
                .setTime(now.minusMinutes(90))
        );
        ExtruderTelemetry entity3 = repository.save(new ExtruderTelemetry()
                .setDevice(device)
                .setCounter(45)
                .setTime(now.minusMinutes(38))
        );
        repository.flush();

        //when
        Optional<List<ExtruderTelemetry>> actual = repository.findTelemetriesInPeriod(
                device.getId(), now.minusMinutes(120), now);

        // then
        assertTrue(actual.isPresent());
        assertEquals(2, actual.get().size());
        assertEquals(Lists.newArrayList(entity2, entity3), actual.get());
    }

    private Device createDevice() {
        Device device = new Device()
                .setName("test device")
                .setUuid(UUID.randomUUID().toString())
                .setDescription("description")
                .setDeviceType(DeviceType.EXTRUDER);
        device = testEntityManager.merge(device);
        testEntityManager.flush();
        return device;
    }

}
