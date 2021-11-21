package com.factory.control.controller.mapper;

import com.factory.control.controller.dto.AggregationSettingsDTO;
import com.factory.control.service.aggregation.AggregationSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.temporal.Temporal;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class AggregationSettingsMapper {
    private final DateTimeMapper dateTimeMapper;

    public <T extends Temporal> AggregationSettings<T> mapToEntity(
            AggregationSettingsDTO aggregationSettingsDTO,
            Function<Temporal, T> conversionMethod
    ) {
        return new AggregationSettings<>(
                dateTimeMapper.mapToType(aggregationSettingsDTO.getFrom(), conversionMethod),
                dateTimeMapper.mapToType(aggregationSettingsDTO.getTo(), conversionMethod),
                aggregationSettingsDTO.getWindowValue(),
                aggregationSettingsDTO.getWindowUnit()
        );
    }
}
