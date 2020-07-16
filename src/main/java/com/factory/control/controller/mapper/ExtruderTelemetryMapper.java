package com.factory.control.controller.mapper;

import com.factory.control.controller.dto.ExtruderTelemetryDTO;
import com.factory.control.domain.entities.ExtruderTelemetry;
import org.mapstruct.*;

import javax.validation.Validator;
import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = Validator.class)
public abstract class ExtruderTelemetryMapper {

    @Mappings({
            @Mapping(target = "counter", source = "dto.counter", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
            @Mapping(target = "density", source = "dto.density", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, qualifiedByName = "DoubleToBigDecimal"),
            @Mapping(target = "diameter", source = "dto.diameter", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, qualifiedByName = "DoubleToBigDecimal")
    })
    public abstract ExtruderTelemetry fromDtoToEntity(ExtruderTelemetryDTO dto);

    @Named("DoubleToBigDecimal")
    public BigDecimal convertDoubleToBigDecimal(Double doubleValue) {
        return BigDecimal.valueOf(doubleValue);
    }
}
