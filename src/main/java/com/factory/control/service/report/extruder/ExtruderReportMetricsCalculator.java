package com.factory.control.service.report.extruder;

import com.factory.control.domain.bo.ExtruderTelemetryReport;
import com.factory.control.domain.entities.ExtruderTelemetry;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;

@Component
public class ExtruderReportMetricsCalculator {

    protected ExtruderTelemetryReport computeReportMetrics(List<ExtruderTelemetry> telemetry,
                                                           OffsetDateTime startOfPeriod, OffsetDateTime endOfPeriod,
                                                           BigDecimal circumference) {
        ExtruderTelemetryReport report = new ExtruderTelemetryReport();
        report.setStartOfPeriod(startOfPeriod);
        report.setEndOfPeriod(endOfPeriod);
        BigDecimal summarizedLength = BigDecimal.valueOf(0.00);
        BigDecimal summarizedWeight = BigDecimal.valueOf(0.00);
        for (ExtruderTelemetry tm : telemetry) {
            BigDecimal bdCounter = BigDecimal.valueOf(tm.getCounter());
            BigDecimal instantLength = circumference.multiply(bdCounter);
            BigDecimal instantVolume = tm.getDiameter()
                    .multiply(tm.getDiameter()).multiply(tm.getDensity())
                    .multiply(instantLength)
                    .divide(BigDecimal.valueOf(4), RoundingMode.HALF_UP);
            summarizedLength = summarizedLength.add(instantLength);
            summarizedWeight = summarizedWeight.add(instantVolume);
        }
        report.setLengthPerformance(convertMillimetersToMeters(summarizedLength));
        report.setWeightPerformance(convertToKilograms(summarizedWeight));
        return report;
    }


    private BigDecimal convertMillimetersToMeters(BigDecimal lengthInMeters) {
        return lengthInMeters.divide(BigDecimal.valueOf(1000), RoundingMode.HALF_UP);
    }

    private BigDecimal convertToKilograms(BigDecimal volumeWithCubicMillimeters) {
        return volumeWithCubicMillimeters.divide(BigDecimal.valueOf(1000000000000L), RoundingMode.HALF_UP);
    }
}
