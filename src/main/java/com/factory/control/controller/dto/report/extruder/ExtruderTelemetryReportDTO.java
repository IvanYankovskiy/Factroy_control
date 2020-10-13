package com.factory.control.controller.dto.report.extruder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
public class ExtruderTelemetryReportDTO implements Serializable {

    private Double lengthPerformance;

    private Double weightPerformance;

    @JsonProperty("startOfPeriod")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime startOfPeriod;

    @JsonProperty("endOfPeriod")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime endOfPeriod;

}
