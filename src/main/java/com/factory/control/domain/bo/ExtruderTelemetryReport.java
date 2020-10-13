package com.factory.control.domain.bo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class ExtruderTelemetryReport {

    private BigDecimal lengthPerformance = BigDecimal.valueOf(0.0);

    private BigDecimal weightPerformance = BigDecimal.valueOf(0.0);

    private OffsetDateTime startOfPeriod;

    private OffsetDateTime endOfPeriod;

}
