package com.factory.control.controller.mapper;

import com.factory.control.controller.dto.DeviceDTO;
import com.factory.control.domain.entities.Device;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {Validator.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DeviceMapper extends DeviceManagementMapper<DeviceDTO, Device> {


    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "deviceType", source = "dto.deviceType")
    @Mapping(target = "description", source = "dto.description")
    Device fromDtoToEntity(DeviceDTO dto);

    @Mapping(target = "uuid", source = "entity.uuid")
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
