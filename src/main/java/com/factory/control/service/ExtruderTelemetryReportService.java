package com.factory.control.service;

import com.factory.control.controller.dto.ExtruderTelemetryReportDTO;
import com.factory.control.controller.mapper.ExtruderTelemetryReportMapper;
import com.factory.control.domain.bo.ExtruderTelemetryReport;
import com.factory.control.domain.entities.Device;
import com.factory.control.domain.entities.ExtruderTelemetry;
import com.factory.control.repository.DeviceRepository;
import com.factory.control.repository.ExtruderTelemetryReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ExtruderTelemetryReportService {

    private final ExtruderTelemetryReportRepository repository;

    private final DeviceRepository deviceRepository;

    private final ExtruderTelemetryReportMapper mapper;

    @Autowired
    public ExtruderTelemetryReportService(ExtruderTelemetryReportRepository repository, DeviceRepository deviceRepository,
                                          ExtruderTelemetryReportMapper mapper) {
        this.repository = repository;
        this.deviceRepository = deviceRepository;
        this.mapper = mapper;
    }

    public ExtruderTelemetryReportDTO getTelemetryReportForLastHour(String token) {
        OffsetDateTime endOfPeriod = OffsetDateTime.now();
        OffsetDateTime startOfPeriod = endOfPeriod.minusHours(1);
        return getTelemetryReportByPeriod(token, startOfPeriod, endOfPeriod);
    }

    public ExtruderTelemetryReportDTO getTelemetryReportForLast12Hours(String token) {
        OffsetDateTime endOfPeriod = OffsetDateTime.now();
        OffsetDateTime startOfPeriod = endOfPeriod.minusHours(12);
        return getTelemetryReportByPeriod(token, startOfPeriod, endOfPeriod);
    }

    public ExtruderTelemetryReportDTO getTelemetryReportForLastWeek(String token) {
        OffsetDateTime endOfPeriod = OffsetDateTime.now();
        OffsetDateTime startOfPeriod = endOfPeriod.minusWeeks(1);
        return getTelemetryReportByPeriod(token, startOfPeriod, endOfPeriod);
    }

    public ExtruderTelemetryReportDTO getTelemetryReportForLastMonth(String token) {
        OffsetDateTime endOfPeriod = OffsetDateTime.now();
        OffsetDateTime startOfPeriod = endOfPeriod.minusMonths(1);
        return getTelemetryReportByPeriod(token, startOfPeriod, endOfPeriod);
    }

    public ExtruderTelemetryReportDTO getTelemetryReportByPeriod(String token, OffsetDateTime startOfPeriod, OffsetDateTime endOfPeriod) {
        Device device = deviceRepository.findByToken(token);
        if (Objects.isNull(device)) {
            throw new RuntimeException("Device with token " + token + " not found");
        }
        List<ExtruderTelemetry> telemetryList = repository
                .findExtruderTelemetriesByDeviceIdIsAndTimeAfterAndTimeBeforeOrderByTime(device, startOfPeriod, endOfPeriod);

        ExtruderTelemetryReport report = computeAverageReportMetrics(telemetryList, startOfPeriod, endOfPeriod);
        return mapper.fromEntityToDTO(report);
    }

    private ExtruderTelemetryReport computeAverageReportMetrics(List<ExtruderTelemetry> telemetry, OffsetDateTime startOfPeriod, OffsetDateTime endOfPeriod) {
        ExtruderTelemetryReport report = new ExtruderTelemetryReport();
        report.setStartOfPeriod(startOfPeriod);
        report.setEndOfPeriod(endOfPeriod);
        BigDecimal summarizedLength = BigDecimal.valueOf(0.00);
        BigDecimal summarizedVolume = BigDecimal.valueOf(0.00);
        for (ExtruderTelemetry tm : telemetry) {
            BigDecimal bdCounter = BigDecimal.valueOf(tm.getCounter());
            BigDecimal instantLength = tm.getDiameter().multiply(bdCounter);
            BigDecimal instantVolume = tm.getDensity().multiply(instantLength);
            summarizedLength = summarizedLength.add(instantLength);
            summarizedVolume = summarizedVolume.add(instantVolume);
        }
        report.setLengthPerformance(summarizedLength);
        report.setVolumetricPerformance(summarizedVolume);
        return report;
    }

}
