package com.factory.control.domain.bo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class ExtruderTelemetryReport {

    private BigDecimal lengthPerformance;

    private BigDecimal volumetricPerformance;

    private OffsetDateTime startOfPeriod;

    private OffsetDateTime endOfPeriod;

}
