package com.factory.control.controller.mapper;

import com.factory.control.controller.dto.DeviceDTO;
import com.factory.control.domain.Device;
import com.factory.control.domain.DeviceType;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class DeviceMapperTest {

    private DeviceMapper deviceMapper = Mappers.getMapper(DeviceMapper.class);

    @Test
    void fromDtoToEntity() {
        DeviceDTO dto = new DeviceDTO();
        dto.setName("device 1");
        dto.setDeviceType("EXTRUDER");
        dto.setDescription("test description");

        Device entity = deviceMapper.fromDtoToEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getName(), entity.getName());
        assertEquals(DeviceType.EXTRUDER, entity.getDeviceType());
        assertEquals(dto.getDescription(), entity.getDescription());
    }
}