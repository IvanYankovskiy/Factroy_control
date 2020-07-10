package com.factory.control.controller.mapper;

import com.factory.control.controller.dto.DeviceDTO;
import com.factory.control.domain.Device;
import com.factory.control.domain.DeviceType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DeviceMapperTest {

    private DeviceMapper deviceMapper = Mappers.getMapper(DeviceMapper.class);

    @Configuration
    @ComponentScan(basePackageClasses = DeviceMapperTest.class)
    public static class MapperWithValidationTestConfig {

        @Bean("defaultValidator")
        public static Validator defaultValidator() {
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            return validatorFactory.getValidator();
        }
    }

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
    void fromEntityToDto() {
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
}