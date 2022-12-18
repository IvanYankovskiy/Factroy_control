package com.factory.control.domain.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Data
@Accessors(chain = true)
public class ExtruderTelemetryDto {
    private Integer deviceId;
    private Integer counter;

    private OffsetDateTime time;

    public ExtruderTelemetryDto(Integer deviceId, Integer counter, OffsetDateTime time) {
        this.deviceId = deviceId;
        this.counter = counter;
        this.time = time;
    }

    public void addCount(Integer count) {
        this.counter += count;
    }
}
