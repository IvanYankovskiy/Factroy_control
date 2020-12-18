package com.factory.control.service.report.extruder;

import com.factory.control.domain.entities.ExtruderTelemetry;
import com.factory.control.domain.entities.ExtruderTelemetryReport;
import com.factory.control.domain.entities.device.Extruder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class ExtruderReportMetricsCalculator {

    protected ExtruderTelemetryReport computeReportMetricsHourly(List<ExtruderTelemetry> telemetry,
                                                                 Extruder device) {
        ExtruderTelemetryReport report = new ExtruderTelemetryReport();
        BigDecimal summarizedLength = BigDecimal.valueOf(0.00);
        BigDecimal summarizedWeight = BigDecimal.valueOf(0.00);
        for (ExtruderTelemetry tm : telemetry) {
            BigDecimal bdCounter = BigDecimal.valueOf(tm.getCounter());
            BigDecimal instantLength = device.getCircumference().multiply(bdCounter);
            BigDecimal instantVolume = tm.getDiameter()
                    .multiply(tm.getDiameter()).multiply(tm.getDensity())
                    .multiply(instantLength)
                    .divide(BigDecimal.valueOf(4), RoundingMode.HALF_UP);
            summarizedLength = summarizedLength.add(instantLength);
            summarizedWeight = summarizedWeight.add(instantVolume);
        }
        report.setLength(convertMillimetersToMeters(summarizedLength));
        report.setWeight(convertToKilograms(summarizedWeight));
        if (telemetry.isEmpty()) {
            report.setTime(OffsetDateTime.now().truncatedTo(ChronoUnit.HOURS));
        } else {
            report.setTime(telemetry.get(telemetry.size() - 1).getTime());
        }
        report.setDevice(device);
        return report;
    }


    private BigDecimal convertMillimetersToMeters(BigDecimal lengthInMeters) {
        return lengthInMeters.divide(BigDecimal.valueOf(1000), RoundingMode.HALF_UP);
    }

    private BigDecimal convertToKilograms(BigDecimal volumeWithCubicMillimeters) {
        return volumeWithCubicMillimeters.divide(BigDecimal.valueOf(1000000000000L), RoundingMode.HALF_UP);
    }
}
