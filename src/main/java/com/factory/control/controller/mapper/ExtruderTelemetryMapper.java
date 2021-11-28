package com.factory.control.controller.mapper;

import com.factory.control.controller.dto.ExtruderTelemetryDTO;
import com.factory.control.domain.entities.ExtruderTelemetry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;

import javax.validation.Validator;

@Mapper(componentModel = "spring", uses = Validator.class)
public abstract class ExtruderTelemetryMapper {

    @Mappings({
            @Mapping(target = "counter", source = "dto.counter", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
    })
    public abstract ExtruderTelemetry fromDtoToEntity(ExtruderTelemetryDTO dto);
}
