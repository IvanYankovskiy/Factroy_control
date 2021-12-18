package com.factory.control.controller.mapper;

import com.factory.control.controller.dto.ExtruderTelemetryDTO;
import com.factory.control.domain.entities.ExtruderTelemetry;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExtruderTelemetryMapperTest {

    private ExtruderTelemetryMapper extruderTelemetryMapper = Mappers.getMapper(ExtruderTelemetryMapper.class);

    @Test
    void test_fromDtoToEntity() {
        ExtruderTelemetryDTO dto = new ExtruderTelemetryDTO();
        dto.setCounter(0);

        ExtruderTelemetry entity = extruderTelemetryMapper.fromDtoToEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getCounter(), entity.getCounter());
    }

}
