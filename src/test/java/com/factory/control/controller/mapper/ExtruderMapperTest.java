package com.factory.control.controller.mapper;

import com.factory.control.controller.dto.ExtruderDTO;
import com.factory.control.domain.entities.device.DeviceType;
import com.factory.control.domain.entities.device.Extruder;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExtruderMapperTest {

    private ExtruderMapper extruderMapper = Mappers.getMapper(ExtruderMapper.class);

    @Test
    void test_fromDtoToEntity_whenCorrectDto_thenCorrectEntity() {
        ExtruderDTO dto = new ExtruderDTO();
        dto.setName("device 1");
        dto.setDeviceType("EXTRUDER");
        dto.setDescription("test description");
        dto.setCircumference(BigDecimal.valueOf(123.85));

        Extruder entity = extruderMapper.fromDtoToEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getName(), entity.getName());
        assertEquals(DeviceType.EXTRUDER, entity.getDeviceType());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getCircumference(), entity.getCircumference());
    }

    @Test
    void test_fromEntityToDto_whenCorrectEntity_thenCorrectDto() {
        Extruder entity = new Extruder();
        entity.setToken("token");
        entity.setName("device 1");
        entity.setDeviceType(DeviceType.EXTRUDER);
        entity.setDescription("test description");
        entity.setCircumference(BigDecimal.valueOf(123.85));

        ExtruderDTO dto = extruderMapper.fromEntityToDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getToken(), dto.getToken());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getDeviceType().name(), DeviceType.EXTRUDER.name());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getCircumference(), dto.getCircumference());
    }

    @Test
    void test_fromEntitiesToDtos_whenCorrectEntities_thenCorrectDtos() {
        Extruder entity1 = new Extruder();
        entity1.setToken("token-1");
        entity1.setName("device 1");
        entity1.setDeviceType(DeviceType.EXTRUDER);
        entity1.setDescription("d1 description");
        entity1.setCircumference(BigDecimal.valueOf(321.85));


        ExtruderDTO expectedDto1 = new ExtruderDTO();
        expectedDto1.setToken(entity1.getToken());
        expectedDto1.setName(entity1.getName());
        expectedDto1.setDescription(entity1.getDescription());
        expectedDto1.setDeviceType(entity1.getDeviceType().name());
        expectedDto1.setCircumference(BigDecimal.valueOf(321.85));


        Extruder entity2 = new Extruder();
        entity2.setToken("token-2");
        entity2.setName("device 2");
        entity2.setDeviceType(DeviceType.PRESS);
        entity2.setDescription("d2 description");
        entity2.setCircumference(BigDecimal.valueOf(987.85));


        ExtruderDTO expectedDto2 = new ExtruderDTO();
        expectedDto2.setToken(entity2.getToken());
        expectedDto2.setName(entity2.getName());
        expectedDto2.setDescription(entity2.getDescription());
        expectedDto2.setDeviceType(entity2.getDeviceType().name());
        expectedDto2.setCircumference(BigDecimal.valueOf(987.85));

        List<Extruder> entities = Arrays.asList(entity1, entity2);
        Set<ExtruderDTO> expectedDtos = new HashSet<>();
        expectedDtos.add(expectedDto1);
        expectedDtos.add(expectedDto2);

        //when
        List<ExtruderDTO> listOfDto = extruderMapper.fromEntitiesToDTOs(entities);

        //then
        assertNotNull(listOfDto);
        assertFalse(listOfDto.isEmpty());
        assertEquals(expectedDtos, new HashSet<>(listOfDto));
    }

}