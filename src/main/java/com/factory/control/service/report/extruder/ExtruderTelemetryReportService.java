package com.factory.control.service.report.extruder;

import com.factory.control.controller.dto.report.extruder.ExtruderTelemetryReportTotalDTO;
import com.factory.control.domain.entities.ExtruderTelemetry;
import com.factory.control.domain.entities.ExtruderTelemetryReport;
import com.factory.control.domain.entities.device.Extruder;
import com.factory.control.repository.ExtruderTelemetryReportRepository;
import com.factory.control.repository.ExtruderTelemetryRepository;
import com.factory.control.repository.device.ExtruderRepository;
import com.factory.control.service.exception.DeviceIsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExtruderTelemetryReportService {

    private final ExtruderTelemetryReportRepository repository;

    private final ExtruderTelemetryRepository extruderTelemetryRepository;

    private final ExtruderRepository extruderRepository;

    private final ExtruderReportMetricsCalculator extruderReportMetricsCalculator;

    private final ExtruderFinalReportGenerator extruderFinalReportGenerator;

    public ExtruderTelemetryReportTotalDTO getTelemetryReportForLastDuration(String token, Duration period) {
        OffsetDateTime endOfPeriod = OffsetDateTime.now().plusHours(1);
        OffsetDateTime startOfPeriod = endOfPeriod.minus(period);
        return getTelemetryReportByPeriodDirectly(token, startOfPeriod, endOfPeriod);
    }

    public ExtruderTelemetryReportTotalDTO getTelemetryReportByPeriodDirectly(String token, OffsetDateTime startOfPeriod, OffsetDateTime endOfPeriod) {
        Extruder device = Optional.of(extruderRepository.findByToken(token))
                .orElseThrow(() -> new DeviceIsNotFoundException(token));
        OffsetDateTime startOfCurrentHour = endOfPeriod.truncatedTo(ChronoUnit.HOURS);

        List<ExtruderTelemetry> telemetryList = extruderTelemetryRepository
                .findTelemetriesInPeriod(device.getId(), startOfCurrentHour, startOfCurrentHour.plusHours(1))
                .orElse(new ArrayList<>());
        ExtruderTelemetryReport currentHourReport = extruderReportMetricsCalculator
                .computeReportMetricsHourly(telemetryList, device);

        LinkedList<ExtruderTelemetryReport> computedReports = repository.findReportsInPeriod(device.getId(), startOfPeriod, endOfPeriod);
        computedReports.addFirst(currentHourReport);

        return extruderFinalReportGenerator.generateReport(computedReports, startOfPeriod, endOfPeriod);
    }
}
