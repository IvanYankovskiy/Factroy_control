package com.factory.control.service.report;

import com.factory.control.controller.dto.ExtruderTelemetryReportTotalDTO;
import com.factory.control.domain.entities.Extruder;
import com.factory.control.domain.entities.ExtruderTelemetry;
import com.factory.control.domain.entities.ExtruderTelemetryReport;
import com.factory.control.repository.ExtruderRepository;
import com.factory.control.repository.ExtruderTelemetryReportRepository;
import com.factory.control.repository.ExtruderTelemetryRepository;
import com.factory.control.service.exception.DeviceIsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.HOURS;

@Deprecated
@Service
@RequiredArgsConstructor
public class ExtruderTelemetryReportService {

    private final ExtruderTelemetryReportRepository repository;
    private final ExtruderTelemetryRepository extruderTelemetryRepository;
    private final ExtruderRepository extruderRepository;
    private final ExtruderReportMetricsCalculator extruderReportMetricsCalculator;
    private final ExtruderFinalReportGenerator extruderFinalReportGenerator;

    public ExtruderTelemetryReportTotalDTO getTelemetryReportForLastDuration(String uuid, Duration period) {
        OffsetDateTime endOfPeriod = OffsetDateTime.now().truncatedTo(HOURS).plusHours(1);
        OffsetDateTime startOfPeriod = endOfPeriod.minus(period);
        return getTelemetryReportByPeriodDirectly(uuid, startOfPeriod, endOfPeriod);
    }

    public ExtruderTelemetryReportTotalDTO getTelemetryReportForLastPeriod(String uuid, Period period) {
        OffsetDateTime endOfPeriod = OffsetDateTime.now().truncatedTo(HOURS).plusHours(1);
        OffsetDateTime startOfPeriod = endOfPeriod.minus(period);
        return getTelemetryReportByPeriodDirectly(uuid, startOfPeriod, endOfPeriod);
    }

    public ExtruderTelemetryReportTotalDTO getTelemetryReportByPeriodDirectly(String uuid, OffsetDateTime startOfPeriod, OffsetDateTime endOfPeriod) {
        Extruder device = Optional.of(extruderRepository.findByUuid(uuid))
                .orElseThrow(() -> new DeviceIsNotFoundException(uuid));
        OffsetDateTime startOfCurrentHour = endOfPeriod.truncatedTo(HOURS).minusHours(1);

        List<ExtruderTelemetry> telemetryList = extruderTelemetryRepository
                .findTelemetriesInPeriod(device.getId(), startOfCurrentHour, endOfPeriod)
                .orElse(new ArrayList<>());
        ExtruderTelemetryReport currentHourReport = extruderReportMetricsCalculator
                .computeReportMetricsHourly(telemetryList, device);

        LinkedList<ExtruderTelemetryReport> computedReports = repository.findReportsInPeriod(
                device.getId(),
                startOfPeriod,
                endOfPeriod
        );
        computedReports.addFirst(currentHourReport);

        ExtruderTelemetryReportTotalDTO finalReport = extruderFinalReportGenerator.generateReport(computedReports, startOfPeriod, endOfPeriod);
        finalReport.setExtruderName(device.getName());
        return finalReport;
    }
}
