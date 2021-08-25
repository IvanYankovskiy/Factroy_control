package com.factory.control.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class ExtruderTelemetryReportDTO implements Serializable {

    private BigDecimal lengthPerformance = BigDecimal.valueOf(0.0);

    private BigDecimal weightPerformance = BigDecimal.valueOf(0.0);

    @JsonProperty("startOfPeriod")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime startOfPeriod;

    @JsonProperty("endOfPeriod")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime endOfPeriod;

    public void addLength(BigDecimal length) {
        this.lengthPerformance = lengthPerformance.add(length);
    }

    public void addWeight(BigDecimal weight) {
        this.weightPerformance = weightPerformance.add(weight);
    }

}
