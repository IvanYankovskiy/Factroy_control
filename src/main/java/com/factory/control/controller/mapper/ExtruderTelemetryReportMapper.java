package com.factory.control.controller.mapper;

import com.factory.control.controller.dto.ExtruderTelemetryReportDTO;
import com.factory.control.domain.bo.ExtruderTelemetryReport;
import org.mapstruct.*;

import java.time.OffsetDateTime;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface ExtruderTelemetryReportMapper {

    @Mappings({
            @Mapping(target = "lengthPerformance", source = "entity.lengthPerformance", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
            @Mapping(target = "volumetricPerformance", source = "entity.volumetricPerformance", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
            @Mapping(target = "startOfPeriod", source = "entity.startOfPeriod", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
            @Mapping(target = "endOfPeriod", source = "entity.endOfPeriod", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    })
    ExtruderTelemetryReportDTO fromEntityToDTO(ExtruderTelemetryReport entity);

    @Named("mapOffsetDateTimeToDate")
    default Date mapOffsetDateTimeToDate(OffsetDateTime from) {
        return Date.from(from.toInstant());
    }

}
