package com.factory.control.controller.dto;

import com.factory.control.domain.entities.ExtruderTelemetry;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Value
public class ExtruderRawTelemetryRecordDTO {

    Integer counter;

    BigDecimal density;

    BigDecimal diameter;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    OffsetDateTime time;

    public ExtruderRawTelemetryRecordDTO(ExtruderTelemetry telemetry) {
        this.counter = telemetry.getCounter();
        this.density = telemetry.getDensity();
        this.diameter = telemetry.getDiameter();
        this.time = telemetry.getTime();
    }


}
