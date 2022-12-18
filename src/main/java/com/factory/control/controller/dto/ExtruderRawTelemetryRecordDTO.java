package com.factory.control.controller.dto;

import com.factory.control.domain.entities.ExtruderTelemetry;
import com.factory.control.domain.entities.ExtruderTelemetryDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
public class ExtruderRawTelemetryRecordDTO {

    Integer counter;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    OffsetDateTime time;

    public ExtruderRawTelemetryRecordDTO(ExtruderTelemetry telemetry) {
        this.counter = telemetry.getCounter();
        this.time = telemetry.getTime();
    }

    public ExtruderRawTelemetryRecordDTO(ExtruderTelemetryDto telemetry) {
        this.counter = telemetry.getCounter();
        this.time = telemetry.getTime();
    }


}
