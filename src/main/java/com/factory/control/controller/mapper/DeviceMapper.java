package com.factory.control.controller.mapper;

import com.factory.control.controller.dto.DeviceDTO;
import com.factory.control.domain.entities.device.Device;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {Validator.class})
public interface DeviceMapper {


    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "deviceType", source = "dto.deviceType")
    @Mapping(target = "description", source = "dto.description")
    Device fromDtoToEntity(DeviceDTO dto);

    @Mapping(target = "token", source = "entity.token")
    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "deviceType", source = "entity.deviceType")
    @Mapping(target = "description", source = "entity.description")
    DeviceDTO fromEntityToDto(Device entity);

    default List<DeviceDTO> fromEntitiesToDTOs(Collection<Device> entities) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }
        return entities
                .stream()
                .map(this::fromEntityToDto)
                .collect(Collectors.toList());
    }

}
