package com.factory.control.config;

import com.factory.control.domain.entities.ExtruderTelemetry;
import com.factory.control.service.aggregation.ExtruderTelemetryProcessor;
import com.factory.control.service.aggregation.TimePeriodBasedAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;

@Configuration
public class AggregationConfig {

    @Bean
    public TimePeriodBasedAggregator<OffsetDateTime, ExtruderTelemetry, ExtruderTelemetry> extruderTelemetryAggregator(
            ExtruderTelemetryProcessor extruderTelemetryProcessor
    ) {
        return new TimePeriodBasedAggregator<>(extruderTelemetryProcessor);
    }

}
