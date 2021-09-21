package com.factory.control.dao_tests;

import com.factory.control.configuration.OnlyDataJpaTest;
import com.factory.control.domain.entities.DeviceType;
import com.factory.control.domain.entities.Extruder;
import com.factory.control.repository.ExtruderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@OnlyDataJpaTest
@DisplayName("ExtruderRepository DAO test")
class ExtruderRepositoryDAOTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ExtruderRepository repository;

    @Test
    void test_saveExtruder() {
        Extruder extruder = new Extruder();
        extruder.setName("Extruder 1 full described");
        extruder.setDeviceType(DeviceType.EXTRUDER);
        extruder.setUuid(UUID.randomUUID().toString());
        extruder.setCircumference(BigDecimal.valueOf(121.56));
        testEntityManager.persistAndFlush(extruder);

        //when
        Optional<Extruder> actual = repository.findById(extruder.getId());
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(extruder, actual.get());
    }
}
