package com.factory.control.service.report.extruder;

import com.factory.control.domain.entities.ExtruderTelemetry;
import com.factory.control.domain.entities.ExtruderTelemetryReport;
import com.factory.control.domain.entities.device.Device;
import com.factory.control.domain.entities.device.Extruder;
import com.factory.control.repository.ExtruderTelemetryReportRepository;
import com.factory.control.repository.ExtruderTelemetryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class ExtruderTelemetryReportGenerator {

    private final ExtruderTelemetryRepository telemetryRepository;
    private final ExtruderReportMetricsCalculator calculator;
    private final ExtruderTelemetryReportRepository telemetryReportRepository;

    @Autowired
    public ExtruderTelemetryReportGenerator(ExtruderTelemetryRepository telemetryRepository,
                                            ExtruderReportMetricsCalculator calculator,
                                            ExtruderTelemetryReportRepository telemetryReportRepository) {
        this.telemetryRepository = telemetryRepository;
        this.calculator = calculator;
        this.telemetryReportRepository = telemetryReportRepository;
    }

    public void generateForAllDevicesInPeriod(OffsetDateTime from, OffsetDateTime to) {
        List<Device> devices = telemetryRepository.selectDistinctDevicesForPeriod(from, to);
        for (Device device : devices) {
            generateReportRecordsForDevice((Extruder) device, from, to);
        }
    }

    //please, read https://www.baeldung.com/spring-data-jpa-pagination-sorting
    //then find official docs and read carefully
    //then explore spring batch and also compare approach
    /**
     * Generates list of complete reports divided hourly
     * 
     * @param device
     * @param from
     * @param to
     * @return
     */
    public List<ExtruderTelemetryReport> generateReportRecordsForDevice(Extruder device, OffsetDateTime from, OffsetDateTime to) {
        List<ExtruderTelemetry> rawTelemetry = telemetryRepository.findTelemetriesInPeriod(device.getId(), from, to)
                .orElse(new ArrayList<>());
        long hoursInPeriod = Duration.between(from, to).toHours();
        int currentRecord = 0;
        List<com.factory.control.domain.entities.ExtruderTelemetryReport> reports = new ArrayList<>();
        for (int currentPeriod = 0; currentPeriod < hoursInPeriod; currentPeriod++) {
            OffsetDateTime currentFrom = from.plusHours(currentPeriod);
            OffsetDateTime currentTo = currentFrom.plusHours(1);
            List<ExtruderTelemetry> subPeriodTelemetry = rawTelemetry
                    .stream()
                    .skip(currentRecord)
                    .filter(telemetry -> currentFrom.isBefore(telemetry.getTime()) && currentTo.isAfter(telemetry.getTime()))
                    .collect(Collectors.toList());
            currentPeriod += subPeriodTelemetry.size();
            if (!subPeriodTelemetry.isEmpty()) {
                reports.add(calculator.computeReportMetricsHourly(subPeriodTelemetry, device));
            }
        }
        if (!reports.isEmpty()) {
            telemetryReportRepository.saveAll(reports);
            telemetryRepository.deleteAll(rawTelemetry);
        }
        return reports;
    }
}
