package com.factory.control.repository;

import com.factory.control.configuration.OnlyDataJpaTest;
import com.factory.control.domain.entities.Device;
import com.factory.control.domain.entities.DeviceType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@OnlyDataJpaTest
@DisplayName("DeviceBaseRepository JPA only test")
class DeviceBaseRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private DeviceBaseRepository<Device, Integer> repository;

    @AfterEach
    void clean() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Returns empty list when all criteria are empty")
    void testReturnEmptyListWhenCriteriaEmpty() {
        // given
        saveDevice("Extruder 1");

        // when
        List<Device> actual = repository.findByCriteria(List.of(), List.of(), List.of());

        // then
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Returns list of devices found only by id")
    void testFindDevicesByIdsOnly() {
        // given
        Device firstExpected = saveDevice("Expected 1");
        Device secondExpected = saveDevice("Expected 2");
        saveDevice("Extra");

        // when
        List<Device> actual = repository.findByCriteria(
                List.of(firstExpected.getId(), secondExpected.getId()),
                List.of(), 
                List.of()
        );

        // then
        assertThat(actual)
                .containsExactlyInAnyOrder(firstExpected, secondExpected);
    }

    @Test
    @DisplayName("Returns list of devices found only by names (names can be duplicated)")
    void testFindDevicesByNamesOnly() {
        // given
        String name = "Expected 1";
        Device firstExpected = saveDevice(name);
        Device secondExpected = saveDevice(name);
        saveDevice("Extra");

        // when
        List<Device> actual = repository.findByCriteria(
                List.of(),
                List.of(name),
                List.of()
        );

        // then
        assertThat(actual)
                .containsExactlyInAnyOrder(firstExpected, secondExpected);
    }

    @Test
    @DisplayName("Returns list of devices found only by uuids")
    void testFindDevicesByUuidsOnly() {
        // given
        Device firstExpected = saveDevice("Expected 1");
        Device secondExpected = saveDevice("Expected 2");
        saveDevice("Extra");

        // when
        List<Device> actual = repository.findByCriteria(
                List.of(),
                List.of(),
                List.of(firstExpected.getUuid(), secondExpected.getUuid())
        );

        // then
        assertThat(actual)
                .containsExactlyInAnyOrder(firstExpected, secondExpected);
    }

    @Test
    @DisplayName("Returns list of distinct devices found by different criteria")
    void testFindDistinctDevicesByDifferentCriteria() {
        // given
        Device firstExpected = saveDevice("Expected 1");
        Device secondExpected = saveDevice("Expected 1");
        Device thirdExpected = saveDevice("Expected 3");
        saveDevice("Extra");

        // when
        List<Device> actual = repository.findByCriteria(
                List.of(firstExpected.getId(), thirdExpected.getId()),
                List.of(secondExpected.getName(), thirdExpected.getName()),
                List.of(firstExpected.getUuid(), secondExpected.getUuid())
        );

        // then
        assertThat(actual)
                .containsExactlyInAnyOrder(firstExpected, secondExpected, thirdExpected);
    }

    private Device createDevice(String name) {
        Device device = new Device();
        device.setName(name);
        device.setDeviceType(DeviceType.EXTRUDER);
        device.setUuid(UUID.randomUUID().toString());
        return device;
    }

    private Device saveDevice(String name) {
        return testEntityManager.persistAndFlush(createDevice(name));
    }

}
