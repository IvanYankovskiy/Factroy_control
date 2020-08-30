package com.factory.control.controller.mapper;

import com.factory.control.controller.dto.DeviceDTO;
import com.factory.control.domain.entities.device.Device;
import com.factory.control.domain.entities.device.DeviceType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DeviceMapperTest {

    private DeviceMapper deviceMapper = Mappers.getMapper(DeviceMapper.class);

    @Test
    void test_fromDtoToEntity_whenCorrectDto_thenCorrectEntity() {
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

    @Test
    void test_fromDtoToEntity_whenNonExistentType_thenIllegalArgumentException() {
        DeviceDTO dto = new DeviceDTO();
        dto.setName("device 1");
        dto.setDeviceType("NO_SUCH_TYPE");
        dto.setDescription("test description");

        assertThrows(IllegalArgumentException.class, () -> {
            deviceMapper.fromDtoToEntity(dto);
        });
    }

    @Test
    void test_fromEntityToDto_whenCorrectEntity_thenCorrectDto() {
        Device entity = new Device();
        entity.setToken("token");
        entity.setName("device 1");
        entity.setDeviceType(DeviceType.EXTRUDER);
        entity.setDescription("test description");

        DeviceDTO dto = deviceMapper.fromEntityToDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getToken(), dto.getToken());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getDeviceType().name(), DeviceType.EXTRUDER.name());
        assertEquals(entity.getDescription(), dto.getDescription());
    }

    @Test
    void test_fromEntitiesToDtos_whenCorrectEntities_thenCorrectDtos() {
        Device entity1 = new Device();
        entity1.setToken("token-1");
        entity1.setName("device 1");
        entity1.setDeviceType(DeviceType.EXTRUDER);
        entity1.setDescription("d1 description");
        
        DeviceDTO expectedDto1 = new DeviceDTO();
        expectedDto1.setToken(entity1.getToken());
        expectedDto1.setName(entity1.getName());
        expectedDto1.setDescription(entity1.getDescription());
        expectedDto1.setDeviceType(entity1.getDeviceType().name());

        Device entity2 = new Device();
        entity2.setToken("token-2");
        entity2.setName("device 2");
        entity2.setDeviceType(DeviceType.PRESS);
        entity2.setDescription("d2 description");

        DeviceDTO expectedDto2 = new DeviceDTO();
        expectedDto2.setToken(entity2.getToken());
        expectedDto2.setName(entity2.getName());
        expectedDto2.setDescription(entity2.getDescription());
        expectedDto2.setDeviceType(entity2.getDeviceType().name());

        List<Device> entities = Arrays.asList(entity1, entity2);
        Set<DeviceDTO> expectedDtos = new HashSet<>();
        expectedDtos.add(expectedDto1);
        expectedDtos.add(expectedDto2);

        //when
        List<DeviceDTO> listOfDto = deviceMapper.fromEntitiesToDTOs(entities);

        //then
        assertNotNull(listOfDto);
        assertFalse(listOfDto.isEmpty());
        assertEquals(expectedDtos, new HashSet<>(listOfDto));
    }
}