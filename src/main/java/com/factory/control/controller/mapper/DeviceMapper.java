package com.factory.control.controller.mapper;

import com.factory.control.controller.dto.DeviceDTO;
import com.factory.control.domain.Device;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    @Mappings({
            @Mapping(target = "name", source = "dto.name", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
            @Mapping(target = "deviceType", source = "dto.deviceType", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
            @Mapping(target = "description", source = "dto.description", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    })
    Device fromDtoToEntity(DeviceDTO dto);

    DeviceDTO fromEntityToDto(Device entity);

}
