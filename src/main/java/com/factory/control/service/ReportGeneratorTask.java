package com.factory.control.service;

import com.factory.control.service.report.ExtruderTelemetryReportGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
public class ReportGeneratorTask {
    private final ExtruderTelemetryReportGenerator extruderTelemetryReportGenerator;

    @Autowired
    public ReportGeneratorTask(ExtruderTelemetryReportGenerator extruderTelemetryReportGenerator) {
        this.extruderTelemetryReportGenerator = extruderTelemetryReportGenerator;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void generateReportForPreviousHour() {
        OffsetDateTime to = OffsetDateTime.now().truncatedTo(ChronoUnit.HOURS);
        OffsetDateTime from = to.minusHours(1);
        log.info("Start generating extruder reports for period [{} - {}]", from, to);
        extruderTelemetryReportGenerator.generateForAllDevicesInPeriod(from, to);
        log.info("Finished generating extruder reports for period [{} - {}]", from, to);
    }
}
