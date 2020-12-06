package com.factory.control.service.report.extruder;

import com.factory.control.controller.dto.report.extruder.ExtruderTelemetryReportDTO;
import com.factory.control.controller.mapper.ExtruderTelemetryReportMapper;
import com.factory.control.domain.bo.ExtruderTelemetryReport;
import com.factory.control.domain.entities.ExtruderTelemetry;
import com.factory.control.domain.entities.device.Extruder;
import com.factory.control.repository.ExtruderTelemetryReportRepository;
import com.factory.control.repository.ExtruderTelemetryRepository;
import com.factory.control.repository.device.ExtruderRepository;
import com.factory.control.service.exception.DeviceIsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExtruderTelemetryReportService {

    private final ExtruderTelemetryReportRepository repository;

    private final ExtruderTelemetryRepository extruderTelemetryRepository;

    private final ExtruderRepository extruderRepository;

    private final ExtruderTelemetryReportMapper mapper;

    private final ExtruderReportMetricsCalculator extruderReportMetricsCalculator;

    @Autowired
    public ExtruderTelemetryReportService(ExtruderTelemetryReportRepository repository,
                                          ExtruderRepository extruderRepository,
                                          ExtruderTelemetryReportMapper extruderTelemetryReportMapper,
                                          ExtruderReportMetricsCalculator extruderReportMetricsCalculator,
                                          ExtruderTelemetryRepository extruderTelemetryRepository) {
        this.repository = repository;
        this.extruderRepository = extruderRepository;
        this.mapper = extruderTelemetryReportMapper;
        this.extruderReportMetricsCalculator = extruderReportMetricsCalculator;
        this.extruderTelemetryRepository = extruderTelemetryRepository;
    }

    public ExtruderTelemetryReportDTO getTelemetryReportForLastDuration(String token, Duration period) {
        OffsetDateTime endOfPeriod = OffsetDateTime.now();
        OffsetDateTime startOfPeriod = endOfPeriod.minus(period);
        return getTelemetryReportByPeriodDirectly(token, startOfPeriod, endOfPeriod);
    }

    public ExtruderTelemetryReportDTO getTelemetryReportByPeriodDirectly(String token, OffsetDateTime startOfPeriod, OffsetDateTime endOfPeriod) {
        Extruder device = Optional.of(extruderRepository.findByToken(token))
                .orElseThrow(() -> new DeviceIsNotFoundException(token));
        List<ExtruderTelemetry> telemetryList = extruderTelemetryRepository
                .findTelemetriesInPeriod(device.getId(), startOfPeriod, endOfPeriod)
                .orElse(new ArrayList<>());

        ExtruderTelemetryReport report = extruderReportMetricsCalculator
                .computeReportMetricsHourly(telemetryList, startOfPeriod, endOfPeriod, device.getCircumference());
        return mapper.fromEntityToDTO(report);
    }

    public void getTelemetryReportByPeriod(String token, OffsetDateTime from, OffsetDateTime to) {
        Extruder device = Optional.of(extruderRepository.findByToken(token))
                .orElseThrow(() -> new DeviceIsNotFoundException(token));
        List<com.factory.control.domain.entities.ExtruderTelemetryReport> reports = repository.findReportsInPeriod(device.getId(), from, to);

    }
}
