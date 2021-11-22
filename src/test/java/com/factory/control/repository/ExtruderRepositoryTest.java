package com.factory.control.repository;

import com.factory.control.configuration.OnlyDataJpaTest;
import com.factory.control.domain.entities.DeviceType;
import com.factory.control.domain.entities.Extruder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@OnlyDataJpaTest
@DisplayName("ExtruderRepository JPA only test")
class ExtruderRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ExtruderRepository repository;

    @AfterEach
    void clean() {
        repository.deleteAll();
    }

    @Test
    void testSaveExtruder() {
        // given
        Extruder extruder = new Extruder();
        extruder.setName("Extruder 1 full described");
        extruder.setDeviceType(DeviceType.EXTRUDER);
        extruder.setUuid(UUID.randomUUID().toString());
        extruder.setCircumference(BigDecimal.valueOf(121.56));
        testEntityManager.persistAndFlush(extruder);

        //when
        Optional<Extruder> actual = repository.findById(extruder.getId());

        // then
        assertTrue(actual.isPresent());
        assertEquals(extruder, actual.get());
    }
}
