package com.factory.control.controller.mapper;

import com.factory.control.controller.dto.ExtruderTelemetryDTO;
import com.factory.control.domain.entities.ExtruderTelemetry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

class ExtruderTelemetryMapperTest {

    private ExtruderTelemetryMapper extruderTelemetryMapper = Mappers.getMapper(ExtruderTelemetryMapper.class);

    @Test
    void test_fromDtoToEntity() {
        ExtruderTelemetryDTO dto = new ExtruderTelemetryDTO();
        dto.setCounter(0);
        dto.setDensity(1.25);
        dto.setDiameter(2.25);

        ExtruderTelemetry entity = extruderTelemetryMapper.fromDtoToEntity(dto);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(dto.getCounter(), entity.getCounter());
        Assertions.assertEquals(BigDecimal.valueOf(dto.getDensity()), entity.getDensity());
        Assertions.assertEquals(BigDecimal.valueOf(dto.getDiameter()), entity.getDiameter());
    }

}